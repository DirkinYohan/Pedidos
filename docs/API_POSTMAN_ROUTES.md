# API Pedidos - Rutas para Postman

Este documento se generó analizando los controladores y servicios de la API.

## Base URL

- Local: `http://localhost:8080`

## Headers recomendados

- `Content-Type: application/json`
- `Accept: application/json`

## Estados y valores útiles

- `EstadoPedido`: `CREADO`, `PAGADO`, `RECOGIDO`, `ENTREGADO`, `CANCELADO`
- `tipoEnvio` (en crear pedido y calcular costo): `DRON`, `EXPRESS`, `INTERNACIONAL`, `ESTANDAR` (o cualquier otro valor para fallback a estándar)

## 1) Clientes (`/api/clientes`)

### 1.1 Crear cliente
- Método: `POST`
- URL: `/api/clientes`
- Body:
```json
{
  "nombre": "Nicolas",
  "email": "nicolas@mail.com",
  "telefono": "3001234567",
  "password": "123456",
  "direccion": "Calle 10 # 20-30",
  "fechaRegistro": "2026-03-26",
  "creditoDisponible": 50000
}
```

### 1.2 Actualizar cliente
- Método: `PUT`
- URL: `/api/clientes/{id}`
- Body:
```json
{
  "nombre": "Nicolas Actualizado",
  "telefono": "3009998888",
  "direccion": "Cra 50 # 12-99"
}
```

### 1.3 Listar clientes
- Método: `GET`
- URL: `/api/clientes`

### 1.4 Obtener cliente por id
- Método: `GET`
- URL: `/api/clientes/{id}`

### 1.5 Eliminar cliente
- Método: `DELETE`
- URL: `/api/clientes/{id}`

---

## 2) Tiendas (`/api/tiendas`)

### 2.1 Crear tienda
- Método: `POST`
- URL: `/api/tiendas`
- Body:
```json
{
  "nombre": "Super Tienda",
  "direccion": "Av Siempre Viva 123",
  "telefono": "6014445566",
  "email": "tienda@mail.com"
}
```

### 2.2 Actualizar tienda
- Método: `PUT`
- URL: `/api/tiendas/{id}`
- Body:
```json
{
  "nombre": "Super Tienda Centro",
  "direccion": "Av Principal 999",
  "telefono": "6017778888",
  "email": "tienda-centro@mail.com"
}
```

### 2.3 Listar tiendas
- Método: `GET`
- URL: `/api/tiendas`

### 2.4 Obtener tienda por id
- Método: `GET`
- URL: `/api/tiendas/{id}`

### 2.5 Eliminar tienda
- Método: `DELETE`
- URL: `/api/tiendas/{id}`

---

## 3) Productos (`/api/productos`)

### 3.1 Crear producto en tienda
- Método: `POST`
- URL: `/api/productos/tienda/{tiendaId}`
- Body:
```json
{
  "nombre": "Arroz 1kg",
  "descripcion": "Arroz premium",
  "precio": 4500,
  "stock": 100,
  "categoria": "Granos"
}
```

### 3.2 Actualizar stock
- Método: `PUT`
- URL: `/api/productos/{id}/stock?stock=80`

### 3.3 Listar por categoría
- Método: `GET`
- URL: `/api/productos/categoria/{categoria}`

### 3.4 Listar por tienda
- Método: `GET`
- URL: `/api/productos/tienda/{tiendaId}`

### 3.5 Listar bajo stock
- Método: `GET`
- URL: `/api/productos/bajo-stock?limite=10`

### 3.6 Obtener producto por id
- Método: `GET`
- URL: `/api/productos/{id}`

### 3.7 Listar todos (según implementación actual)
- Método: `GET`
- URL: `/api/productos`
- Nota: actualmente llama `listarProductosPorCategoria(null)`, por lo que depende del comportamiento del repositorio para `categoria = null`.

---

## 4) Repartidores (`/api/repartidores`)

### 4.1 Crear repartidor
- Método: `POST`
- URL: `/api/repartidores`
- Body:
```json
{
  "nombre": "Carlos Rider",
  "email": "rider@mail.com",
  "telefono": "3110001111",
  "password": "abc123",
  "vehiculo": "Moto",
  "licencia": "A2-XYZ",
  "calificacion": 5.0,
  "disponible": true
}
```

### 4.2 Listar disponibles
- Método: `GET`
- URL: `/api/repartidores/disponibles`

### 4.3 Actualizar disponibilidad
- Método: `PUT`
- URL: `/api/repartidores/{id}/disponibilidad?disponible=false`

### 4.4 Listar todos
- Método: `GET`
- URL: `/api/repartidores`

### 4.5 Obtener repartidor por id
- Método: `GET`
- URL: `/api/repartidores/{id}`

---

## 5) Pedidos (`/api/pedidos`)

### 5.1 Crear pedido
- Método: `POST`
- URL: `/api/pedidos/crear`
- Body:
```json
{
  "clienteId": 1,
  "tiendaId": 1,
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 2,
      "cantidad": 1
    }
  ],
  "direccionEntrega": "Calle 100 # 10-20",
  "emailConfirmacion": "cliente@mail.com",
  "tipoEnvio": "EXPRESS"
}
```

### 5.2 Actualizar estado de pedido
- Método: `PUT`
- URL: `/api/pedidos/{id}/estado?estado=PAGADO`

### 5.3 Registrar pago
- Método: `POST`
- URL: `/api/pedidos/{id}/pago`
- Body:
```json
{
  "metodoPago": "TARJETA",
  "monto": 25000
}
```

### 5.4 Solicitar devolución
- Método: `POST`
- URL: `/api/pedidos/{id}/devolucion`
- Body:
```json
{
  "motivo": "Producto defectuoso",
  "montoReembolso": 25000
}
```

### 5.5 Asignar repartidor
- Método: `PUT`
- URL: `/api/pedidos/{id}/asignar-repartidor?repartidorId=1`

### 5.6 Listar pedidos por cliente
- Método: `GET`
- URL: `/api/pedidos/cliente/{clienteId}`

### 5.7 Listar pedidos por estado
- Método: `GET`
- URL: `/api/pedidos/estado/{estado}`

### 5.8 Obtener pedido por id
- Método: `GET`
- URL: `/api/pedidos/{id}`

### 5.9 Listar todos los pedidos
- Método: `GET`
- URL: `/api/pedidos`

---

## 6) Envíos (`/api/envios`)

### 6.1 Actualizar tracking
- Método: `PUT`
- URL: `/api/envios/{id}/tracking?trackingNumber=TRK-123456`

### 6.2 Calcular costo de envío
- Método: `GET`
- URL: `/api/envios/calcular-costo?tipoEnvio=DRON`

### 6.3 Obtener envío por pedido
- Método: `GET`
- URL: `/api/envios/pedido/{pedidoId}`

### 6.4 Obtener envío por tracking
- Método: `GET`
- URL: `/api/envios/tracking/{trackingNumber}`

### 6.5 Listar envíos
- Método: `GET`
- URL: `/api/envios`

### 6.6 Obtener envío por id
- Método: `GET`
- URL: `/api/envios/{id}`

---

## 7) Pagos (`/api/pagos`)

### 7.1 Obtener pago por pedido
- Método: `GET`
- URL: `/api/pagos/pedido/{pedidoId}`

### 7.2 Listar pagos
- Método: `GET`
- URL: `/api/pagos`

### 7.3 Obtener pago por id
- Método: `GET`
- URL: `/api/pagos/{id}`

---

## 8) Transacciones (`/api/transacciones`)

### 8.1 Obtener transacción por pedido
- Método: `GET`
- URL: `/api/transacciones/pedido/{pedidoId}`

### 8.2 Listar transacciones
- Método: `GET`
- URL: `/api/transacciones`

### 8.3 Obtener transacción por id
- Método: `GET`
- URL: `/api/transacciones/{id}`

---

## Flujo sugerido de prueba en Postman

1. Crear tienda.
2. Crear productos para esa tienda.
3. Crear cliente.
4. Crear repartidor.
5. Crear pedido.
6. Registrar pago del pedido.
7. Asignar repartidor.
8. Actualizar estado del pedido a `ENTREGADO`.
9. Solicitar devolución (si quieres probar ese caso).

## Observaciones técnicas del código

- Muchos endpoints que fallan en servicios lanzan `RuntimeException`; sin un `@ControllerAdvice`, esto normalmente responde como `500`.
- En `PedidoController`, varios métodos capturan excepción y devuelven `400` con texto plano.
- `GET /api/productos` usa `findByCategoria(null)`, no `findAll()`.
- Entidades JPA con relaciones bidireccionales podrían causar respuestas muy anidadas o problemas de serialización si no se manejan anotaciones JSON.
