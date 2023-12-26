package com.products.service;

import org.springframework.stereotype.Service;

import com.products.entity.Usuario;
import com.products.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario obtenerUsuario(Long id) {
        return usuarioRepository.getReferenceById(id);
    }
    
}
