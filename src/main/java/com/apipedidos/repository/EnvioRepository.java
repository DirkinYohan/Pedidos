package com.apipedidos.repository;


import com.apipedidos.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByPedidoId(Long pedidoId);
    Optional<Envio> findByTrackingNumber(String trackingNumber);
}



