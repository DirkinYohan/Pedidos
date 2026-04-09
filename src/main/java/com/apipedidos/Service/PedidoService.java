package com.apipedidos.Service;

import com.apipedidos.DTO.DetallePedidoRequest;
import com.apipedidos.Model.*;
import com.apipedidos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
/**
 * Servicio que orquesta la creación, actualización y operaciones relacionadas
 * con `Pedido` (crear pedido, registrar pagos, solicitudes de devolución,
 * asignar repartidores, etc.).
 *
 * Nota: la lógica del IVA se centraliza aquí temporalmente con `IVA_RATE`.
 */
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;
    
    @Autowired
    private DevolucionRepository devolucionRepository;
    
    @Autowired
    private EnvioRepository envioRepository;
    
    private static final double IVA_RATE = 1.19;
    // Nota: `IVA_RATE` representa el multiplicador para convertir base -> precio con IVA.
    // Por ejemplo, para IVA 19%: base * 1.19 = precioConIva.
    // Este valor se usa en la lógica KISS para calcular base e IVA cuando el detalle
    // solo proporciona `subtotal` (precio con IVA). En entornos donde cada producto
    // tiene su propia tasa de IVA, es preferible usar los campos ya calculados en
    // `DetallePedido` (`subtotalSinIva` y `ivaUnitarioAmount`) para evitar discrepancias.
    
    @Transactional
    public Pedido crearPedido(Long clienteId, Long tiendaId, List<DetallePedidoRequest> detalles, 
                              String direccionEntrega, String emailConfirmacion, String tipoEnvio) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        Tienda tienda = tiendaRepository.findById(tiendaId)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        
        Pedido pedido = new Pedido(cliente, tienda, direccionEntrega, emailConfirmacion);
        
        Double subtotal = 0.0;
        for (DetallePedidoRequest detalleReq : detalles) {
            Producto producto = productoRepository.findById(detalleReq.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            if (producto.getStock() < detalleReq.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            
            DetallePedido detalle = new DetallePedido(detalleReq.getCantidad(), producto, pedido);
            pedido.getDetalles().add(detalle);
            subtotal += detalle.getSubtotal();
            
            producto.setStock(producto.getStock() - detalleReq.getCantidad());
            productoRepository.save(producto);
        }
        
        pedido.setSubtotal(subtotal);

        double ivaTotal = 0.0;
        double totalSinIva = 0.0;
        if (pedido.getDetalles() != null) {
            for (DetallePedido d : pedido.getDetalles()) {
                int cantidad = d.getCantidad() != null ? d.getCantidad() : 0;
                // KISS: preferir sumar desde los campos ya calculados en DetallePedido
                // (si están presentes) para respetar tasas por producto. Use el
                // siguiente orden de preferencia:
                // 1) `subtotalSinIva` + `ivaUnitarioAmount * cantidad` (más preciso)
                // 2) si faltan, derivar desde `subtotal` (precio con IVA) dividiendo
                //    por `IVA_RATE`.

                if (d.getSubtotalSinIva() != null) {
                    // Usamos subtotal sin IVA directamente (ya incluye cantidad)
                    totalSinIva += d.getSubtotalSinIva();
                } else if (d.getPrecioSinIva() != null) {
                    // Si solo hay precio sin IVA por unidad, multiplicar por cantidad
                    totalSinIva += d.getPrecioSinIva() * cantidad;
                } else if (d.getSubtotal() != null) {
                    // Si solo tenemos subtotal (con IVA), derivamos la base
                    double subtotalConIva = d.getSubtotal();
                    double base = subtotalConIva / IVA_RATE;
                    totalSinIva += base;
                }

                if (d.getIvaUnitarioAmount() != null) {
                    ivaTotal += d.getIvaUnitarioAmount() * cantidad;
                } else if (d.getSubtotal() != null) {
                    // Si no hay IVA unitario, derivar desde subtotal y base calculada
                    double subtotalConIva = d.getSubtotal();
                    double base = subtotalConIva / IVA_RATE;
                    ivaTotal += subtotalConIva - base;
                }
            }
        }
        pedido.setIvaTotal(ivaTotal);
        pedido.setTotalSinIva(totalSinIva);

        Envio envio = crearEnvioPorTipo(tipoEnvio);
        envio.setPedido(pedido);
        Double costoEnvio = envio.calcularCosto();
        envio.setCostoTotal(costoEnvio);
        pedido.setEnvio(envioRepository.save(envio));

        pedido.setTotal(subtotal + costoEnvio);

        return pedidoRepository.save(pedido);
    }
    /**
     * Crea un nuevo `Pedido` con sus `DetallePedido` y cálculo de totales.
     *
     * Flujo:
     * - Valida cliente y tienda.
     * - Crea las líneas de detalle usando `DetallePedido` (que calcula precios/IVA).
     * - Calcula `subtotal`, `totalSinIva` e `ivaTotal` sumando los campos de cada detalle
     *   (con fallback a derivar desde `subtotal` si el detalle no provee los campos).
     * - Crea y persiste `Envio` según `tipoEnvio` y suma su costo al total.
     * - Persiste el `Pedido` (cascade guarda los `DetallePedido`).
     *
     * @param clienteId id del cliente que realiza el pedido
     * @param tiendaId id de la tienda donde se realiza el pedido
     * @param detalles lista de líneas (`DetallePedidoRequest`) con `productoId` y `cantidad`
     * @param direccionEntrega dirección para entrega
     * @param emailConfirmacion email para notificación
     * @param tipoEnvio tipo de envío (DRON, EXPRESS, INTERNACIONAL, etc.)
     * @return el `Pedido` persistido con totales y envío
     */
    
    private Envio crearEnvioPorTipo(String tipo) {
        if (tipo == null) {
            return new EnvioEstandar(false);
        }
        
        switch (tipo.toUpperCase()) {
            case "DRON":
                return new EnvioDron(5.0, true);
            case "EXPRESS":
                return new EnvioExpress(true, 15.0);
            case "INTERNACIONAL":
                return new EnvioInternacional("Default", 30.0, true);
            default:
                return new EnvioEstandar(false);
        }
    }
    
    @Transactional
    public Pedido actualizarEstadoPedido(Long pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado(nuevoEstado);
        enviarEmailConfirmacion(pedido);
        
        return pedidoRepository.save(pedido);
    }
    /**
     * Actualiza el estado de un pedido y envía una confirmación por email (simulada).
     * @param pedidoId id del pedido a actualizar
     * @param nuevoEstado nuevo estado a establecer
     * @return Pedido actualizado
     */
    
    @Transactional
    public Pago registrarPago(Long pedidoId, String metodoPago, Double monto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (pedido.getEstado() != EstadoPedido.CREADO) {
            throw new RuntimeException("El pedido no está en estado CREADO para realizar el pago");
        }
        
        Pago pago = new Pago(monto, metodoPago, pedido);
        pago.setEstado(EstadoPago.COMPLETADO);
        pago = pagoRepository.save(pago);

        pedido.setPago(pago);
        pedido.setEstado(EstadoPedido.PAGADO);

        if (pedido.getTransaccion() == null) {
            Transaccion transaccion = new Transaccion("PAGO", monto, "PAY-" + System.currentTimeMillis(), pedido);
            transaccionRepository.save(transaccion);
            pedido.setTransaccion(transaccion);
        }

        pedidoRepository.save(pedido);

        return pago;
    }
    /**
     * Registra un pago para el pedido indicado.
     * - Valida que el pedido esté en estado `CREADO`.
     * - Crea un `Pago` y una `Transaccion` asociada.
     * - Cambia el estado del pedido a `PAGADO`.
     *
     * @param pedidoId id del pedido
     * @param metodoPago descripción del método de pago (ej. 'CARD')
     * @param monto monto pagado
     * @return el objeto `Pago` persistido
     */
    
    @Transactional
    public Devolucion solicitarDevolucion(Long pedidoId, String motivo, Double montoReembolso) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (pedido.getEstado() != EstadoPedido.ENTREGADO) {
            throw new RuntimeException("Solo se pueden devolver pedidos entregados");
        }
        
        Devolucion devolucion = new Devolucion(motivo, montoReembolso, pedido);
        devolucion = devolucionRepository.save(devolucion);
        pedido.setDevolucion(devolucion);
        pedido.setEstado(EstadoPedido.CANCELADO);
        
        pedidoRepository.save(pedido);
        
        return devolucion;
    }
    /**
     * Crea una solicitud de devolución asociada a un pedido entregado.
     * Cambia el estado del pedido a `CANCELADO` y persiste la `Devolucion`.
     * @param pedidoId id del pedido
     * @param motivo motivo de la devolución
     * @param montoReembolso monto a reembolsar
     * @return la `Devolucion` creada
     */
    
    @Transactional
    public Pedido asignarRepartidor(Long pedidoId, Long repartidorId, RepartidorService repartidorService) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        Repartidor repartidor = repartidorService.buscarPorId(repartidorId);
        
        pedido.setRepartidor(repartidor);
        
        if (pedido.getEstado() == EstadoPedido.PAGADO) {
            pedido.setEstado(EstadoPedido.RECOGIDO);
        }
        
        return pedidoRepository.save(pedido);
    }
    /**
     * Asigna un `Repartidor` a un pedido.
     * - Si el pedido estaba en `PAGADO`, se marca como `RECOGIDO`.
     * @param pedidoId id del pedido
     * @param repartidorId id del repartidor a asignar
     * @param repartidorService servicio para buscar al repartidor
     * @return pedido actualizado
     */
    
    private void enviarEmailConfirmacion(Pedido pedido) {
        System.out.println("Enviando email a: " + pedido.getEmailConfirmacion());
        System.out.println("Pedido #" + pedido.getId() + " - Estado: " + pedido.getEstado());
    }
    /**
     * Método auxiliar (simulado) para enviar confirmaciones por email.
     * No envía correos reales: solo imprime en consola para desarrollo.
     */
    
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    /**
     * Lista pedidos realizados por un cliente.
     * @param clienteId id del cliente
     * @return lista de pedidos
     */
    
    public List<Pedido> listarPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
    /**
     * Lista pedidos filtrando por estado.
     * @param estado estado del pedido
     * @return lista de pedidos
     */
    
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
    /**
     * Obtiene un pedido por su id o lanza `RuntimeException` si no existe.
     * @param id id del pedido
     * @return Pedido encontrado
     */
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
}
