package com.apipedidos.Model;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_envio")
/**
 * Clase base para distintos tipos de envíos (estándar, express, dron, internacional).
 *
 * Implementaciones concretas deben sobrescribir `calcularCosto()`.
 */
public abstract class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String trackingNumber;
    private Double costoBase;
    private Double costoTotal;
    private Integer tiempoEstimadoDias;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    public Envio() {}
    
    public Envio(Double costoBase, Integer tiempoEstimadoDias) {
        this.costoBase = costoBase;
        this.tiempoEstimadoDias = tiempoEstimadoDias;
        this.trackingNumber = generarTrackingNumber();
    }
    
    private String generarTrackingNumber() {
        return "TRK-" + System.currentTimeMillis();
    }
    
    public abstract Double calcularCosto();
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    
    public Double getCostoBase() { return costoBase; }
    public void setCostoBase(Double costoBase) { this.costoBase = costoBase; }
    
    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }
    
    public Integer getTiempoEstimadoDias() { return tiempoEstimadoDias; }
    public void setTiempoEstimadoDias(Integer tiempoEstimadoDias) { this.tiempoEstimadoDias = tiempoEstimadoDias; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}



