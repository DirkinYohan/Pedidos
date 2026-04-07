package com.apipedidos.Service;

import com.apipedidos.Model.Tienda;
import com.apipedidos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
/**
 * Servicio para operaciones CRUD sobre `Tienda`.
 */
public class TiendaService {
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    public Tienda registrarTienda(Tienda tienda) {
        if (tiendaRepository.findByEmail(tienda.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return tiendaRepository.save(tienda);
    }
    
    public Tienda actualizarTienda(Long id, Tienda tiendaActualizada) {
        Tienda tienda = tiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        
        tienda.setNombre(tiendaActualizada.getNombre());
        tienda.setDireccion(tiendaActualizada.getDireccion());
        tienda.setTelefono(tiendaActualizada.getTelefono());
        tienda.setEmail(tiendaActualizada.getEmail());
        
        return tiendaRepository.save(tienda);
    }
    
    public List<Tienda> listarTodos() {
        return tiendaRepository.findAll();
    }
    
    public Tienda buscarPorId(Long id) {
        return tiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
    }
    
    public void eliminarTienda(Long id) {
        tiendaRepository.deleteById(id);
    }
}



