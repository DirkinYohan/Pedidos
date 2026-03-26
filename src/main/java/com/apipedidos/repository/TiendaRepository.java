package com.apipedidos.repository;


import com.apipedidos.Model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {
    Optional<Tienda> findByEmail(String email);
}



