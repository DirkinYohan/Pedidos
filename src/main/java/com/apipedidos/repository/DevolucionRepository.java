package com.apipedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apipedidos.Model.Devolucion;

import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
    Optional<Devolucion> findByPedidoId(Long pedidoId);
}