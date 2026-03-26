package com.apipedidos.Service;

import com.apipedidos.Model.Pago;
import com.apipedidos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
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
}



