package com.apipedidos.Service;

import com.apipedidos.Model.Repartidor;
import com.apipedidos.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepartidorService {
    
    @Autowired
    private RepartidorRepository repartidorRepository;
    
    public Repartidor registrarRepartidor(Repartidor repartidor) {
        if (repartidorRepository.findByEmail(repartidor.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return repartidorRepository.save(repartidor);
    }
    
    public List<Repartidor> listarRepartidoresDisponibles() {
        return repartidorRepository.findByDisponibleTrue();
    }
    
    public Repartidor actualizarDisponibilidad(Long id, Boolean disponible) {
        Repartidor repartidor = repartidorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repartidor no encontrado"));
        repartidor.setDisponible(disponible);
        return repartidorRepository.save(repartidor);
    }
    
    public List<Repartidor> listarTodos() {
        return repartidorRepository.findAll();
    }
    
    public Repartidor buscarPorId(Long id) {
        return repartidorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Repartidor no encontrado"));
    }
}



