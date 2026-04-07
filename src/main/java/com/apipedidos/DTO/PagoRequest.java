package com.apipedidos.DTO;

/**
 * DTO usado al registrar un pago sobre un `Pedido`.
 * Contiene el `metodoPago` y el `monto` pagado.
 */
public class PagoRequest {
    private String metodoPago;
    private Double monto;
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}