package com.apipedidos.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
/**
 * Entidad que representa un pedido realizado por un cliente.
 *
 * Contiene metadata del pedido (fecha, estado), totales y la relación
 * con los `DetallePedido` que son las líneas del pedido.
 */
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaPedido;
    private Double subtotal;
    private Double total;
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    
    private String direccionEntrega;
    private String emailConfirmacion;
    private Double ivaTotal;
    private Double totalSinIva;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;
    
    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private Repartidor repartidor;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pago pago;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Devolucion devolucion;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Transaccion transaccion;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Envio envio;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles = new ArrayList<>();
    
    public Pedido() {}
    
    public Pedido(Cliente cliente, Tienda tienda, String direccionEntrega, String emailConfirmacion) {
        this.fechaPedido = LocalDateTime.now();
        this.cliente = cliente;
        this.tienda = tienda;
        this.direccionEntrega = direccionEntrega;
        this.emailConfirmacion = emailConfirmacion;
        this.estado = EstadoPedido.CREADO;
        this.subtotal = 0.0;
        this.total = 0.0;
        this.ivaTotal = 0.0;
        this.totalSinIva = 0.0;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public Double getIvaTotal() { return ivaTotal; }
    public void setIvaTotal(Double ivaTotal) { this.ivaTotal = ivaTotal; }
    
    public Double getTotalSinIva() { return totalSinIva; }
    public void setTotalSinIva(Double totalSinIva) { this.totalSinIva = totalSinIva; }
    
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    
    public String getEmailConfirmacion() { return emailConfirmacion; }
    public void setEmailConfirmacion(String emailConfirmacion) { this.emailConfirmacion = emailConfirmacion; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    
    public Repartidor getRepartidor() { return repartidor; }
    public void setRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }
    
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    
    public Devolucion getDevolucion() { return devolucion; }
    public void setDevolucion(Devolucion devolucion) { this.devolucion = devolucion; }
    
    public Transaccion getTransaccion() { return transaccion; }
    public void setTransaccion(Transaccion transaccion) { this.transaccion = transaccion; }
    
    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }
    
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}



