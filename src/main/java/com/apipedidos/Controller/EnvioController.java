package com.apipedidos.Controller;


import com.apipedidos.Model.Envio;
import com.apipedidos.Service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {
    
    @Autowired
    private EnvioService envioService;
    
    @PutMapping("/{id}/tracking")
    public ResponseEntity<Envio> actualizarTracking(@PathVariable Long id, @RequestParam String trackingNumber) {
        return ResponseEntity.ok(envioService.actualizarTracking(id, trackingNumber));
    }
    
    @GetMapping("/calcular-costo")
    public ResponseEntity<Double> calcularCosto(@RequestParam String tipoEnvio) {
        return ResponseEntity.ok(envioService.calcularCostoEnvio(tipoEnvio));
    }
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Envio> obtenerPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(envioService.obtenerEnvioPorPedido(pedidoId));
    }
    
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Envio> obtenerPorTracking(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(envioService.obtenerEnvioPorTracking(trackingNumber));
    }
    
    @GetMapping
    public ResponseEntity<List<Envio>> listarTodos() {
        return ResponseEntity.ok(envioService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.buscarPorId(id));
    }
}



