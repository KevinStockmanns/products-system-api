package com.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.HistorialPrecio;

public interface HistorialPrecioRepository extends JpaRepository<HistorialPrecio, Long> {
    
}
