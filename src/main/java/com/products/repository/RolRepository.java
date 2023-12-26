package com.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{

    Optional<Rol> findByNombre(String string);
    
}
