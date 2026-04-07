package com.apipedidos.Service;

import com.apipedidos.Model.Devolucion;
import com.apipedidos.repository.DevolucionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
/**
 * Servicio para consultar devoluciones y listados.
 */
public class DevolucionService {
    
    @Autowired
    private DevolucionRepository devolucionRepository;
    
    public Devolucion buscarPorPedido(Long pedidoId) {
        return devolucionRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("Devolución no encontrada para este pedido"));
    }
    
    public List<Devolucion> listarTodos() {
        return devolucionRepository.findAll();
    }
    
    public Devolucion buscarPorId(Long id) {
        return devolucionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Devolución no encontrada"));
    }
}



