package com.apipedidos.Service;

import com.apipedidos.Model.Transaccion;
import com.apipedidos.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransaccionService {
    /**
     * Servicio para consultar `Transaccion` relacionadas con pedidos.
     *
     * Provee métodos de lectura simples: buscar por pedido, listar todas
     * y buscar por id. Las operaciones lanzan `RuntimeException` si no
     * se encuentra la entidad (comportamiento consistente con otros
     * servicios del proyecto).
     */

    @Autowired
    private TransaccionRepository transaccionRepository;

    /**
     * Busca la transacción asociada a un pedido específico.
     * @param pedidoId id del pedido
     * @return Transaccion asociada
     */
    public Transaccion buscarPorPedido(Long pedidoId) {
        return transaccionRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("Transacción no encontrada para este pedido"));
    }

    /**
     * Lista todas las transacciones almacenadas.
     * @return lista de transacciones
     */
    public List<Transaccion> listarTodos() {
        return transaccionRepository.findAll();
    }

    /**
     * Busca una transacción por su id.
     * @param id id de la transacción
     * @return Transaccion encontrada
     */
    public Transaccion buscarPorId(Long id) {
        return transaccionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }
}



