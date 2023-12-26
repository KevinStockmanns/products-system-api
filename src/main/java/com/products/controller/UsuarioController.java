package com.products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.usuario.UsuarioRespuestaDTO;
import com.products.entity.Usuario;
import com.products.service.UsuarioService;
import com.products.utils.payload.ResponseWrapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ResponseWrapper<UsuarioRespuestaDTO>> getUsuario(
        @PathVariable Long id
    ){
        Usuario usuario = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok().body(new ResponseWrapper<UsuarioRespuestaDTO>(
            HttpStatus.OK,
            null,
            null,
            new UsuarioRespuestaDTO(usuario)));
    }
}
