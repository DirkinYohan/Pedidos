package com.apipedidos.repository;

import com.apipedidos.Model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {
    List<Repartidor> findByDisponibleTrue();
    Optional<Repartidor> findByEmail(String email);
}



