package com.apipedidos.repository;

import com.apipedidos.Model.Pedido;
import com.apipedidos.Model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByRepartidorId(Long repartidorId);
    List<Pedido> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);
}



