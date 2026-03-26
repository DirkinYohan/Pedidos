package com.apipedidos.Service;

import com.apipedidos.Model.Transaccion;
import com.apipedidos.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransaccionService {
    
    @Autowired
    private TransaccionRepository transaccionRepository;
    
    public Transaccion buscarPorPedido(Long pedidoId) {
        return transaccionRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("Transacción no encontrada para este pedido"));
    }
    
    public List<Transaccion> listarTodos() {
        return transaccionRepository.findAll();
    }
    
    public Transaccion buscarPorId(Long id) {
        return transaccionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }
}



