package com.apipedidos.Model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devolucion")
/**
 * Representa una solicitud de devolución asociada a un `Pedido`.
 * Contiene el motivo, monto a reembolsar y estado de la devolución.
 */
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String motivo;
    private Double montoReembolso;
    private LocalDateTime fechaDevolucion;
    private String estado;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    public Devolucion() {}
    
    public Devolucion(String motivo, Double montoReembolso, Pedido pedido) {
        this.motivo = motivo;
        this.montoReembolso = montoReembolso;
        this.pedido = pedido;
        this.fechaDevolucion = LocalDateTime.now();
        this.estado = "SOLICITADA";
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public Double getMontoReembolso() { return montoReembolso; }
    public void setMontoReembolso(Double montoReembolso) { this.montoReembolso = montoReembolso; }
    
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}



