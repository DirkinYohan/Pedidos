package com.apipedidos.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "detalle_pedido")
/**
 * Representa un detalle de un pedido (una línea de producto).
 *
 * Contiene información calculada en el constructor para facilitar el
 * persistido: `precioUnitario`, `precioSinIva`, `ivaUnitarioAmount`,
 * `subtotal` y `subtotalSinIva`.
 *
 * Notas:
 * - Si el `Producto` ya provee `precioSinIva`, se usa directamente.
 * - Si el `Producto` expone `iva` (porcentaje), se calcula la base dividiendo
 *   el precio con IVA entre (1 + iva%) para obtener el precio sin IVA.
 */
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Double precioSinIva;
    private Double ivaUnitarioAmount;
    private Double subtotalSinIva;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    public DetallePedido() {}
    
    public DetallePedido(Integer cantidad, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
        this.precioUnitario = producto.getPrecio();
        BigDecimal precioConIva = BigDecimal.valueOf(this.precioUnitario != null ? this.precioUnitario : 0.0);
        Double ivaPorc = producto.getIva();

        // Calcular precio sin IVA por unidad
        if (producto.getPrecioSinIva() != null) {
            // Producto ya trae precio sin IVA
            this.precioSinIva = producto.getPrecioSinIva();
        } else if (ivaPorc != null) {
            // Producto define % IVA (ej: 19 -> 0.19)
            BigDecimal ivaRate = BigDecimal.valueOf(ivaPorc).divide(BigDecimal.valueOf(100));
            this.precioSinIva = precioConIva.divide(BigDecimal.ONE.add(ivaRate), 2, RoundingMode.HALF_UP).doubleValue();
        } else {
            // Fallback a 19% si no hay información del producto
            BigDecimal ivaRate = BigDecimal.valueOf(0.19);
            this.precioSinIva = precioConIva.divide(BigDecimal.ONE.add(ivaRate), 2, RoundingMode.HALF_UP).doubleValue();
        }

        // IVA unitario en monto (precio con IVA - precio sin IVA)
        this.ivaUnitarioAmount = BigDecimal.valueOf(this.precioUnitario != null ? this.precioUnitario : 0.0)
                .subtract(BigDecimal.valueOf(this.precioSinIva != null ? this.precioSinIva : 0.0))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Subtotales (con y sin IVA) multiplicando por la cantidad
        this.subtotal = (this.precioUnitario != null ? this.precioUnitario : 0.0) * (cantidad != null ? cantidad : 0);
        this.subtotalSinIva = (this.precioSinIva != null ? this.precioSinIva : 0.0) * (cantidad != null ? cantidad : 0);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getPrecioSinIva() { return precioSinIva; }
    public void setPrecioSinIva(Double precioSinIva) { this.precioSinIva = precioSinIva; }

    public Double getIvaUnitarioAmount() { return ivaUnitarioAmount; }
    public void setIvaUnitarioAmount(Double ivaUnitarioAmount) { this.ivaUnitarioAmount = ivaUnitarioAmount; }

    public Double getSubtotalSinIva() { return subtotalSinIva; }
    public void setSubtotalSinIva(Double subtotalSinIva) { this.subtotalSinIva = subtotalSinIva; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}



