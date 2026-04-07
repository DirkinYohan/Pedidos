package com.apipedidos.DTO;

/**
 * DTO para solicitar una devolución sobre un `Pedido`.
 * Contiene el motivo y el monto a reembolsar.
 */
public class DevolucionRequest {
    private String motivo;
    private Double montoReembolso;
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Double getMontoReembolso() { return montoReembolso; }
    public void setMontoReembolso(Double montoReembolso) { this.montoReembolso = montoReembolso; }
}