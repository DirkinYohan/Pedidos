package com.apipedidos.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
/**
 * Registra eventos financieros o de sistema relacionados con un `Pedido`.
 * Ejemplos: pagos, reembolsos, conciliaciones.
 */
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo;
    private Double monto;
    private LocalDateTime fechaTransaccion;
    private String referencia;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    public Transaccion() {}
    
    public Transaccion(String tipo, Double monto, String referencia, Pedido pedido) {
        this.tipo = tipo;
        this.monto = monto;
        this.referencia = referencia;
        this.pedido = pedido;
        this.fechaTransaccion = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public LocalDateTime getFechaTransaccion() { return fechaTransaccion; }
    public void setFechaTransaccion(LocalDateTime fechaTransaccion) { this.fechaTransaccion = fechaTransaccion; }
    
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}



