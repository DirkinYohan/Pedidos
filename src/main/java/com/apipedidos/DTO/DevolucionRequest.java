package com.apipedidos.DTO;

public class DevolucionRequest {
    private String motivo;
    private Double montoReembolso;
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Double getMontoReembolso() { return montoReembolso; }
    public void setMontoReembolso(Double montoReembolso) { this.montoReembolso = montoReembolso; }
}