package com.apipedidos.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
/**
 * Registro de pago asociado a un `Pedido`.
 * Contiene el monto, el método y la fecha en que se realizó el pago.
 */
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String estado;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    public Pago() {}
    
    public Pago(Double monto, String metodoPago, Pedido pedido) {
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.pedido = pedido;
        this.fechaPago = LocalDateTime.now();
        this.estado = "COMPLETADO";
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}



