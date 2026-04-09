package com.apipedidos.Controller;


import com.apipedidos.Model.Pago;
import com.apipedidos.Model.EstadoPago;
import com.apipedidos.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
/**
 * Controlador para exponer endpoints de consulta sobre pagos.
 */
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Pago> obtenerPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pagoService.buscarPorPedido(pedidoId));
    }
    
    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPorId(id));
    }

    /**
     * Endpoint para actualizar el estado de un pago (confirmación desde pasarela).
     * Ejemplo: POST /api/pagos/123/estado?estado=COMPLETADO
     */
    @PostMapping("/{id}/estado")
    public ResponseEntity<Pago> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        EstadoPago nuevoEstado;
        try {
            nuevoEstado = EstadoPago.valueOf(estado.toUpperCase());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(pagoService.actualizarEstadoPago(id, nuevoEstado));
    }
}



