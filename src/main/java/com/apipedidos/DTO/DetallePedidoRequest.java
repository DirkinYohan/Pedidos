package com.apipedidos.DTO;

/**
 * DTO que representa una línea de pedido en la creación de un `Pedido`.
 * Solo contiene el `productoId` y la `cantidad` solicitada.
 */
public class DetallePedidoRequest {
    private Long productoId;
    private Integer cantidad;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
