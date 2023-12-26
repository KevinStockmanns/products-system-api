package com.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.products.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombre(String nombre);

    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.vistas) DESC")
    Page<Producto> findAllByEstadoAndOrderedVistasDesc(Pageable paginacion, Boolean activo);

    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.vistas) ASC")
    Page<Producto> findAllByEstadoAndOrderedVistasAsc(Pageable paginacion, Boolean activo);
    
    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.ventas) DESC")
    Page<Producto> findAllByEstadoAndOrderedVentasDesc(Pageable paginacion, Boolean activo);

    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.ventas) ASC")
    Page<Producto> findAllByEstadoAndOrderedVentasAsc(Pageable paginacion, Boolean activo);
    
    @Query("SELECT p FROM Producto p WHERE estado = :activo ORDER BY p.nombre ASC")
    Page<Producto> findAllByEstadoAndOrderedNombreAsc(Pageable paginacion, Boolean activo);

    @Query("SELECT p FROM Producto p WHERE estado = :activo ORDER BY p.nombre DESC")
    Page<Producto> findAllByEstadoAndOrderedNombreDesc(Pageable paginacion, Boolean activo);

    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.pedidos) ASC")
    Page<Producto> findAllByEstadoAndOrderedPedidosAsc(Pageable paginacion, Boolean activo);

    @Query("SELECT p FROM Producto p JOIN p.versiones v WHERE p.estado = :activo GROUP BY p ORDER BY SUM(v.pedidos) DESC")
    Page<Producto> findAllByEstadoAndOrderedPedidosDesc(Pageable paginacion, Boolean activo);


}
