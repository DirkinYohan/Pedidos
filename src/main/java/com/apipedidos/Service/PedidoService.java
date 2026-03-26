package com.apipedidos.Service;

import com.apipedidos.DTO.DetallePedidoRequest;
import com.apipedidos.Model.*;
import com.apipedidos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
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
    private DevolucionRepository devolucionRepository;
    
    @Autowired
    private TransaccionRepository transaccionRepository;
    
    @Autowired
    private EnvioRepository envioRepository;
    
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
        
        Envio envio = crearEnvioPorTipo(tipoEnvio);
        envio.setPedido(pedido);
        Double costoEnvio = envio.calcularCosto();
        envio.setCostoTotal(costoEnvio);
        pedido.setEnvio(envioRepository.save(envio));
        
        pedido.setTotal(subtotal + costoEnvio);
        
        return pedidoRepository.save(pedido);
    }
    
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
    
    @Transactional
    public Pago registrarPago(Long pedidoId, String metodoPago, Double monto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (pedido.getEstado() != EstadoPedido.CREADO) {
            throw new RuntimeException("El pedido no está en estado CREADO para realizar el pago");
        }
        
        Pago pago = new Pago(monto, metodoPago, pedido);
        pago = pagoRepository.save(pago);
        
        pedido.setPago(pago);
        pedido.setEstado(EstadoPedido.PAGADO);
        
        Transaccion transaccion = new Transaccion("PAGO", monto, "PAY-" + System.currentTimeMillis(), pedido);
        transaccionRepository.save(transaccion);
        pedido.setTransaccion(transaccion);
        
        pedidoRepository.save(pedido);
        
        return pago;
    }
    
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
    
    private void enviarEmailConfirmacion(Pedido pedido) {
        System.out.println("Enviando email a: " + pedido.getEmailConfirmacion());
        System.out.println("Pedido #" + pedido.getId() + " - Estado: " + pedido.getEstado());
    }
    
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    public List<Pedido> listarPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
}
