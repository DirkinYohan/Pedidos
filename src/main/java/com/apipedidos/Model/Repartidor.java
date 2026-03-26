package com.apipedidos.Model;


import jakarta.persistence.Entity;

@Entity
public class Repartidor extends Usuario {
    private String vehiculo;
    private String licencia;
    private Double calificacion;
    private Boolean disponible;
    
    public Repartidor() {}
    
    public Repartidor(String nombre, String email, String telefono, String password,
                      String vehiculo, String licencia) {
        super(nombre, email, telefono, password);
        this.vehiculo = vehiculo;
        this.licencia = licencia;
        this.calificacion = 5.0;
        this.disponible = true;
    }
    
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    
    public String getLicencia() { return licencia; }
    public void setLicencia(String licencia) { this.licencia = licencia; }
    
    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
    
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}



