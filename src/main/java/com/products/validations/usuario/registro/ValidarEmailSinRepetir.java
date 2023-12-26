package com.products.validations.usuario.registro;

import org.springframework.stereotype.Component;

import com.products.dto.auth.AuthRegisterDTO;
import com.products.error.LogicError;
import com.products.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidarEmailSinRepetir implements ValidarRegistroUsuario{

    private final UsuarioRepository usuarioRepository;

    @Override
    public void validar(AuthRegisterDTO datos) {
        if(usuarioRepository.findByCorreo(datos.correo()).isPresent())
            throw new LogicError("correo", "El correo ingresado ya está en uso.");
    }
    
}
