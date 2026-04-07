package com.apipedidos.Controller;

import com.apipedidos.Model.Repartidor;
import com.apipedidos.Service.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
/**
 * Controlador para gestionar `Repartidor` (registro y disponibilidad).
 */
public class RepartidorController {
    
    @Autowired
    private RepartidorService repartidorService;
    
    @PostMapping
    public ResponseEntity<Repartidor> registrar(@RequestBody Repartidor repartidor) {
        return ResponseEntity.ok(repartidorService.registrarRepartidor(repartidor));
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<Repartidor>> listarDisponibles() {
        return ResponseEntity.ok(repartidorService.listarRepartidoresDisponibles());
    }
    
    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<Repartidor> actualizarDisponibilidad(@PathVariable Long id, @RequestParam Boolean disponible) {
        return ResponseEntity.ok(repartidorService.actualizarDisponibilidad(id, disponible));
    }
    
    @GetMapping
    public ResponseEntity<List<Repartidor>> listarTodos() {
        return ResponseEntity.ok(repartidorService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Repartidor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repartidorService.buscarPorId(id));
    }
}



