package com.apipedidos.repository;


import com.apipedidos.Model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    Optional<Transaccion> findByPedidoId(Long pedidoId);
}



