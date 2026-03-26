package com.apipedidos.Controller;

import com.apipedidos.Model.Tienda;
import com.apipedidos.Service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {
    
    @Autowired
    private TiendaService tiendaService;
    
    @PostMapping
    public ResponseEntity<Tienda> registrar(@RequestBody Tienda tienda) {
        return ResponseEntity.ok(tiendaService.registrarTienda(tienda));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizar(@PathVariable Long id, @RequestBody Tienda tienda) {
        return ResponseEntity.ok(tiendaService.actualizarTienda(id, tienda));
    }
    
    @GetMapping
    public ResponseEntity<List<Tienda>> listarTodos() {
        return ResponseEntity.ok(tiendaService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tienda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tiendaService.buscarPorId(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
        return ResponseEntity.ok().build();
    }
}



