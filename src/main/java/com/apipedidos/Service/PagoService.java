package com.apipedidos.Service;

import com.apipedidos.Model.*;
import com.apipedidos.repository.PagoRepository;
import com.apipedidos.repository.TransaccionRepository;
import com.apipedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
/**
 * Servicio para gestionar pagos. Incluye consulta y cambio de estado
 * (confirmaciones desde la pasarela).
 */
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    public Pago buscarPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado para este pedido"));
    }

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Long id) {
        return pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    /**
     * Actualiza el estado del pago. Si el pago queda `COMPLETADO`, se marca
     * el `Pedido` como `PAGADO` y se crea la `Transaccion` asociada.
     * @param pagoId id del pago a actualizar
     * @param nuevoEstado nuevo estado a asignar
     * @return Pago actualizado
     */
    @Transactional
    public Pago actualizarEstadoPago(Long pagoId, EstadoPago nuevoEstado) {
        Pago pago = buscarPorId(pagoId);
        pago.setEstado(nuevoEstado);
        pago = pagoRepository.save(pago);

        Pedido pedido = pago.getPedido();
        if (pedido == null) return pago;

        if (nuevoEstado == EstadoPago.COMPLETADO) {
            if (pedido.getTransaccion() == null) {
                Transaccion t = new Transaccion("PAGO", pago.getMonto(), "PAY-" + System.currentTimeMillis(), pedido);
                transaccionRepository.save(t);
                pedido.setTransaccion(t);
            }
            pedido.setEstado(EstadoPedido.PAGADO);
            pedidoRepository.save(pedido);
        } else if (nuevoEstado == EstadoPago.RECHAZADO) {
            // Evitar degradar pedidos que ya están pagados
            if (pedido.getEstado() != EstadoPedido.PAGADO) {
                pedido.setEstado(EstadoPedido.CREADO);
                pedidoRepository.save(pedido);
            }
        } else if (nuevoEstado == EstadoPago.CANCELADO) {
            // Evitar degradar pedidos que ya están pagados
            if (pedido.getEstado() != EstadoPedido.PAGADO) {
                pedido.setEstado(EstadoPedido.CANCELADO);
                pedidoRepository.save(pedido);
            }
        }

        return pago;
    }
}



