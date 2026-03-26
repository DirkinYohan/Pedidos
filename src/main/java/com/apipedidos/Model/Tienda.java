package com.apipedidos.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();
    
    @OneToMany(mappedBy = "tienda")
    private List<Pedido> pedidos = new ArrayList<>();
    
    public Tienda() {}
    
    public Tienda(String nombre, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}



