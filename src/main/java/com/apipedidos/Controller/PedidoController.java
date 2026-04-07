package com.apipedidos.Controller;

import com.apipedidos.DTO.*;
import com.apipedidos.Model.*;
import com.apipedidos.Service.PedidoService;
import com.apipedidos.Service.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
/**
 * Controlador que expone endpoints para crear y gestionar `Pedido`.
 *
 * Endpoints principales:
 * - `POST /crear` crear un pedido (usa `CrearPedidoRequest`).
 * - `PUT /{id}/estado` actualizar estado del pedido.
 * - `POST /{id}/pago` registrar pago (usa `PagoRequest`).
 * - `POST /{id}/devolucion` solicitar devolución (usa `DevolucionRequest`).
 */
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private RepartidorService repartidorService;
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@RequestBody CrearPedidoRequest request) {
        try {
            Pedido pedido = pedidoService.crearPedido(
                request.getClienteId(),
                request.getTiendaId(),
                request.getDetalles(),
                request.getDireccionEntrega(),
                request.getEmailConfirmacion(),
                request.getTipoEnvio()
            );
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Endpoint para crear un pedido.
     * - Valida y crea el pedido usando `PedidoService.crearPedido`.
     * - Devuelve el pedido creado o un error en caso de validación.
     * @param request DTO con datos del pedido
     */
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        try {
            Pedido pedido = pedidoService.actualizarEstadoPedido(id, estado);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Actualiza el estado de un pedido. Parámetro `estado` debe ser uno de `EstadoPedido`.
     */
    
    @PostMapping("/{id}/pago")
    public ResponseEntity<?> registrarPago(@PathVariable Long id, @RequestBody PagoRequest request) {
        try {
            Pago pago = pedidoService.registrarPago(id, request.getMetodoPago(), request.getMonto());
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Registra un pago para el pedido.
     * @param request DTO `PagoRequest` con `metodoPago` y `monto`.
     */
    
    @PostMapping("/{id}/devolucion")
    public ResponseEntity<?> solicitarDevolucion(@PathVariable Long id, @RequestBody DevolucionRequest request) {
        try {
            Devolucion devolucion = pedidoService.solicitarDevolucion(id, request.getMotivo(), request.getMontoReembolso());
            return ResponseEntity.ok(devolucion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Solicita una devolución para un pedido entregado.
     * @param request DTO `DevolucionRequest` con motivo y monto.
     */
    
    @PutMapping("/{id}/asignar-repartidor")
    public ResponseEntity<?> asignarRepartidor(@PathVariable Long id, @RequestParam Long repartidorId) {
        try {
            Pedido pedido = pedidoService.asignarRepartidor(id, repartidorId, repartidorService);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Asigna un repartidor al pedido indicado.
     */
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorCliente(clienteId));
    }
    /**
     * Lista pedidos por cliente.
     */
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> listarPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorEstado(estado));
    }
    /**
     * Lista pedidos filtrando por estado.
     */
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Obtiene un pedido por id. Devuelve 404 si no existe.
     */
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
}
