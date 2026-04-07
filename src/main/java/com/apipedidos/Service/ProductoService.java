package com.apipedidos.Service;

import com.apipedidos.Model.Producto;
import com.apipedidos.Model.Tienda;
import com.apipedidos.repository.ProductoRepository;
import com.apipedidos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
/**
 * Servicio para CRUD y utilidades relacionadas con `Producto`.
 *
 * Incluye helpers para calcular `precioSinIva` y `iva` cuando alguno falta.
 */
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    public Producto crearProducto(Producto producto, Long tiendaId) {
        Tienda tienda = tiendaRepository.findById(tiendaId)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        
        producto.setTienda(tienda);
        if (producto.getPrecio() == null) {
            throw new RuntimeException("El producto debe tener un precio");
        }

        Double precio = producto.getPrecio();
        Double iva = producto.getIva();
        Double precioSinIva = producto.getPrecioSinIva();

        if (iva == null && precioSinIva == null) {
            double defaultRate = 19.0;
            precioSinIva = computePrecioSinIva(precio, defaultRate);
            iva = defaultRate;
        } else if (precioSinIva == null && iva != null) {
            precioSinIva = computePrecioSinIva(precio, iva);
        } else if (iva == null && precioSinIva != null) {
            iva = computeIvaPercent(precio, precioSinIva);
        }

        producto.setPrecioSinIva(precioSinIva);
        producto.setIva(iva);

        return productoRepository.save(producto);
    }
    /**
     * Crea un nuevo `Producto` en la tienda indicada y asegura que los campos
     * `precioSinIva` e `iva` estén completos. Comportamiento:
     * - Si faltan ambos, asume tasa por defecto 19% y calcula `precioSinIva`.
     * - Si falta `precioSinIva` y existe `iva`, lo calcula a partir del precio.
     * - Si falta `iva` y existe `precioSinIva`, deriva el porcentaje.
     *
     * @param producto entidad con datos iniciales
     * @param tiendaId id de la tienda que contiene el producto
     * @return producto persistido con `precioSinIva` e `iva` completos
     */
    
    public Producto actualizarStock(Long productoId, Integer nuevoStock) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }
    /**
     * Actualiza el stock de un producto.
     * @param productoId id del producto
     * @param nuevoStock nuevo valor de stock
     * @return producto actualizado
     */
    
    public List<Producto> listarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    /**
     * Lista productos por categoría. Si `categoria` es null devuelve todos.
     */
    
    public List<Producto> listarProductosPorTienda(Long tiendaId) {
        return productoRepository.findByTiendaId(tiendaId);
    }

    /**
     * Lista productos pertenecientes a una tienda.
     */
    
    public List<Producto> listarProductosConBajoStock(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }

    /**
     * Encuentra productos con stock menor que `limite`.
     */
    
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    /**
     * Busca un producto por id o lanza `RuntimeException` si no existe.
     */

    public Producto eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
        return null;
    }
    /**
     * Calcula el precio sin IVA a partir de `precioConIva` y `ivaPercent`.
     * @param precioConIva precio que incluye IVA
     * @param ivaPercent porcentaje de IVA (ej. 19.0)
     * @return precio sin IVA con escala 2
     */
    private Double computePrecioSinIva(Double precioConIva, Double ivaPercent) {
        if (precioConIva == null || ivaPercent == null) return null;
        java.math.BigDecimal precio = java.math.BigDecimal.valueOf(precioConIva);
        java.math.BigDecimal ivaRate = java.math.BigDecimal.valueOf(ivaPercent).divide(java.math.BigDecimal.valueOf(100));
        java.math.BigDecimal precioSin = precio.divide(java.math.BigDecimal.ONE.add(ivaRate), 2, java.math.RoundingMode.HALF_UP);
        return precioSin.doubleValue();
    }

    private Double computeIvaPercent(Double precioConIva, Double precioSinIva) {
        if (precioConIva == null || precioSinIva == null) return null;
        java.math.BigDecimal precio = java.math.BigDecimal.valueOf(precioConIva);
        java.math.BigDecimal sin = java.math.BigDecimal.valueOf(precioSinIva);
        if (sin.compareTo(java.math.BigDecimal.ZERO) == 0) return 0.0;
        java.math.BigDecimal ivaAmount = precio.subtract(sin);
        java.math.BigDecimal ivaPercent = ivaAmount.divide(sin, 4, java.math.RoundingMode.HALF_UP).multiply(java.math.BigDecimal.valueOf(100));
        return ivaPercent.setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
    }
}