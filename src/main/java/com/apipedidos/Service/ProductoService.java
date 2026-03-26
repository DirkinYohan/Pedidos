package com.apipedidos.Service;

import com.apipedidos.Model.Producto;
import com.apipedidos.Model.Tienda;
import com.apipedidos.repository.ProductoRepository;
import com.apipedidos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    public Producto crearProducto(Producto producto, Long tiendaId) {
        Tienda tienda = tiendaRepository.findById(tiendaId)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        
        producto.setTienda(tienda);
        return productoRepository.save(producto);
    }
    
    public Producto actualizarStock(Long productoId, Integer nuevoStock) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }
    
    public List<Producto> listarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> listarProductosPorTienda(Long tiendaId) {
        return productoRepository.findByTiendaId(tiendaId);
    }
    
    public List<Producto> listarProductosConBajoStock(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }
    
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
}



