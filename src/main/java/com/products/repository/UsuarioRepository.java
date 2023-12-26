package com.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);
    
}
