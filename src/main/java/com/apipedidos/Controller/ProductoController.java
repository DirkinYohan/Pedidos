package com.apipedidos.Controller;

import com.apipedidos.Model.Producto;
import com.apipedidos.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
/**
 * Controlador para gestión y consulta de `Producto`.
 */
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @PostMapping("/tienda/{tiendaId}")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto, @PathVariable Long tiendaId) {
        return ResponseEntity.ok(productoService.crearProducto(producto, tiendaId));
    }
    
    @PutMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        return ResponseEntity.ok(productoService.actualizarStock(id, stock));
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.listarProductosPorCategoria(categoria));
    }
    
    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<List<Producto>> listarPorTienda(@PathVariable Long tiendaId) {
        return ResponseEntity.ok(productoService.listarProductosPorTienda(tiendaId));
    }
    
    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Producto>> listarBajoStock(@RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity.ok(productoService.listarProductosConBajoStock(limite));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarProductosPorCategoria(null));
    }
}



