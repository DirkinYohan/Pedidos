package com.apipedidos.Controller;


import com.apipedidos.Model.Cliente;
import com.apipedidos.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @PostMapping
    public ResponseEntity<Cliente> registrar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.registrarCliente(cliente));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente));
    }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok().build();
    }
}



