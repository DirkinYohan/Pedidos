package com.apipedidos.Service;

import com.apipedidos.Model.*;
import com.apipedidos.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
/**
 * Servicio para operaciones relacionadas con envíos (tracking y cálculo de costo).
 */
public class EnvioService {
    
    @Autowired
    private EnvioRepository envioRepository;
    
    public Envio actualizarTracking(Long envioId, String trackingNumber) {
        Envio envio = envioRepository.findById(envioId)
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        
        envio.setTrackingNumber(trackingNumber);
        return envioRepository.save(envio);
    }
    
    public Double calcularCostoEnvio(String tipoEnvio) {
        Envio envio;
        switch (tipoEnvio.toUpperCase()) {
            case "DRON":
                envio = new EnvioDron(5.0, true);
                break;
            case "EXPRESS":
                envio = new EnvioExpress(true, 15.0);
                break;
            case "INTERNACIONAL":
                envio = new EnvioInternacional("Default", 30.0, true);
                break;
            default:
                envio = new EnvioEstandar(false);
        }
        return envio.calcularCosto();
    }
    
    public Envio obtenerEnvioPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId)
            .orElseThrow(() -> new RuntimeException("No se encontró envío para este pedido"));
    }
    
    public Envio obtenerEnvioPorTracking(String trackingNumber) {
        return envioRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }
    
    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }
    
    public Envio buscarPorId(Long id) {
        return envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }
}



