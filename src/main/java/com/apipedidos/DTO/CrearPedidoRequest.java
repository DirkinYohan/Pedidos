package com.apipedidos.DTO;

import java.util.List;

public class CrearPedidoRequest {
    private Long clienteId;
    private Long tiendaId;
    private List<DetallePedidoRequest> detalles;
    private String direccionEntrega;
    private String emailConfirmacion;
    private String tipoEnvio;
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getTiendaId() { return tiendaId; }
    public void setTiendaId(Long tiendaId) { this.tiendaId = tiendaId; }
    public List<DetallePedidoRequest> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoRequest> detalles) { this.detalles = detalles; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public String getEmailConfirmacion() { return emailConfirmacion; }
    public void setEmailConfirmacion(String emailConfirmacion) { this.emailConfirmacion = emailConfirmacion; }
    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }
}