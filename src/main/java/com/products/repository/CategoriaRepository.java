package com.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNombre(String nombre);

    boolean existsByNombre(String nombre);
    
}
