Proyecto ApiPedidos — Guía de IVA y cambios comunes

Resumen
- Este proyecto implementa un servicio de pedidos simple con modelos `Pedido`, `DetallePedido`, `Producto`, etc.
- La lógica de IVA puede venir de varias fuentes: `Producto.iva`, `Producto.precioSinIva`, o los valores calculados dentro de `DetallePedido`.

Reglas y recomendaciones sobre IVA
- Fuente preferida: use los campos calculados por `DetallePedido` cuando estén disponibles:
  - `DetallePedido.subtotalSinIva` (base por línea, ya multiplicada por cantidad)
  - `DetallePedido.ivaUnitarioAmount` (monto de IVA por unidad)
  - Sumar `subtotalSinIva` y `ivaUnitarioAmount * cantidad` garantiza consistencia en presencia de tasas por producto.
- Fallback KISS: cuando solo exista `subtotal` (precio con IVA), el código actual divide por `IVA_RATE` para derivar la base:
  - `base = subtotalConIva / IVA_RATE` (ej. `IVA_RATE = 1.19` para 19%)
  - `iva = subtotalConIva - base`
- Evitar usar una constante global si hay productos con tasas diferentes. Es aceptable como fallback o para tiendas que usan una sola tasa.

Dónde cambiar la tasa global
- `src/main/java/com/apipedidos/Service/PedidoService.java` define actualmente `IVA_RATE` como `1.19`.
- Para hacerlo configurable, mueva esa propiedad a `application.properties`:

  application.properties
  ----------------------
  app.iva.rate=1.19

  y en `PedidoService` inyecte con `@Value("${app.iva.rate:1.19}") private double ivaRate;`

Cambios comunes y cómo aplicarlos
- Cambiar tasa de IVA por tienda/producto:
  - Añadir campo `iva` en `Tienda` o `Producto` y actualizar la creación de `DetallePedido` para usar esa tasa.
- Forzar recalculo de subtotales:
  - Modifique el constructor de `DetallePedido` o añada un método `recalcular()` que actualice `precioSinIva`, `ivaUnitarioAmount`, `subtotal`, `subtotalSinIva`.
- Tests recomendados:
  - Añadir pruebas unitarias para: cálculo de `DetallePedido`, suma de `ivaTotal` y `totalSinIva` en `PedidoService`, casos con y sin `precioSinIva`.

Guía rápida de desarrollo
- Compilar y correr tests:

```bash
./mvnw test
``` 

- Ejecutar la aplicación localmente:

```bash
./mvnw spring-boot:run
```

Próximos pasos sugeridos
- Documentar controladores y DTOs (ya hay documentación en los modelos y servicios).
- Añadir tests unitarios para la lógica de IVA.

Flujo de pagos y estados
- Los pagos se crean inicialmente en estado `PENDIENTE` al llamar `POST /api/pedidos/{id}/pago`.
- La pasarela de pagos debe confirmar el resultado mediante el endpoint:
  `POST /api/pagos/{pagoId}/estado?estado=COMPLETADO` (u otro estado: `RECHAZADO`, `CANCELADO`, `AUTORIZADO`).
- Al confirmar `COMPLETADO` la API creará una `Transaccion` y marcará el `Pedido` como `PAGADO`.

Colección Postman
- Se añadió la request `Actualizar estado pago (confirmación)` en `docs/ApiPedidos.postman_collection.json`.


Contacto
- Para más cambios, deja una nota en el repositorio o pídeme que genere pruebas y/o muevas la configuración del IVA a `application.properties`.
