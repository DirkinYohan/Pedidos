package com.apipedidos.Model;

import jakarta.persistence.*;

@Entity
/**
 * Representa un producto vendible en la tienda.
 *
 * Campos importantes:
 * - `precio`: precio con IVA incluido (si es el caso).
 * - `iva`: porcentaje de IVA aplicado (ej. 19.0 para 19%).
 * - `precioSinIva`: opcional, precio base sin IVA si ya está precalculado.
 */
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private Double iva;
    private Double precioSinIva;

    
    
    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;
    
    public Producto() {}
    
    public Producto(String nombre, String descripcion, Double precio, Integer stock, String categoria, Double iva, Double precioSinIva) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.iva = iva;
        this.precioSinIva = precioSinIva;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    public Double getPrecioSinIva() { return precioSinIva; }
    public void setPrecioSinIva(Double precioSinIva) { this.precioSinIva = precioSinIva; }
}



