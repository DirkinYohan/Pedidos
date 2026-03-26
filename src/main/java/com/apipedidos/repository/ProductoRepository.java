package com.apipedidos.repository;

import com.apipedidos.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByTiendaId(Long tiendaId);
    List<Producto> findByStockLessThan(Integer stock);
}



