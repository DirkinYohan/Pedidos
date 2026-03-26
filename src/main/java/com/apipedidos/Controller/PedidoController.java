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
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        try {
            Pedido pedido = pedidoService.actualizarEstadoPedido(id, estado);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/pago")
    public ResponseEntity<?> registrarPago(@PathVariable Long id, @RequestBody PagoRequest request) {
        try {
            Pago pago = pedidoService.registrarPago(id, request.getMetodoPago(), request.getMonto());
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/devolucion")
    public ResponseEntity<?> solicitarDevolucion(@PathVariable Long id, @RequestBody DevolucionRequest request) {
        try {
            Devolucion devolucion = pedidoService.solicitarDevolucion(id, request.getMotivo(), request.getMontoReembolso());
            return ResponseEntity.ok(devolucion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/asignar-repartidor")
    public ResponseEntity<?> asignarRepartidor(@PathVariable Long id, @RequestParam Long repartidorId) {
        try {
            Pedido pedido = pedidoService.asignarRepartidor(id, repartidorId, repartidorService);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorCliente(clienteId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> listarPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorEstado(estado));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
}
