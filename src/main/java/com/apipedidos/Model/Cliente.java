package com.apipedidos.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
/**
 * Representa un cliente registrado que puede realizar pedidos.
 * Extiende `Usuario` con información adicional como dirección,
 * fecha de registro y crédito disponible.
 */
public class Cliente extends Usuario {
    private String direccion;
    private LocalDate fechaRegistro;
    private Double creditoDisponible;
    
    public Cliente() {}
    
    public Cliente(String nombre, String email, String telefono, String password, 
                   String direccion, Double creditoDisponible) {
        super(nombre, email, telefono, password);
        this.direccion = direccion;
        this.fechaRegistro = LocalDate.now();
        this.creditoDisponible = creditoDisponible;
    }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Double getCreditoDisponible() { return creditoDisponible; }
    public void setCreditoDisponible(Double creditoDisponible) { this.creditoDisponible = creditoDisponible; }
}



