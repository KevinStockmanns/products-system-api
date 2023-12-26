package com.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.Pedido;
import com.products.entity.Usuario;
import com.products.utils.enums.EstadoPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByEstado(Pageable paginacion, EstadoPedido estadoPedido);

    Page<Pedido> findByEstadoAndUsuario(Pageable paginacion, EstadoPedido estado, Usuario usuario);
    
}
