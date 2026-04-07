package com.apipedidos.Controller;

import com.apipedidos.Model.Transaccion;
import com.apipedidos.Service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
/**
 * Controlador para exponer consultas sobre `Transaccion`.
 */
public class TransaccionController {
    
    @Autowired
    private TransaccionService transaccionService;
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Transaccion> obtenerPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(transaccionService.buscarPorPedido(pedidoId));
    }
    
    @GetMapping
    public ResponseEntity<List<Transaccion>> listarTodos() {
        return ResponseEntity.ok(transaccionService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.buscarPorId(id));
    }
}



