package com.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long>{
    
}
