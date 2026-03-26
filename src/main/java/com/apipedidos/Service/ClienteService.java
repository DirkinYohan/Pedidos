package com.apipedidos.Service;


import com.apipedidos.Model.Cliente;
import com.apipedidos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public Cliente registrarCliente(Cliente cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return clienteRepository.save(cliente);
    }
    
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setDireccion(clienteActualizado.getDireccion());
        
        return clienteRepository.save(cliente);
    }
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}



