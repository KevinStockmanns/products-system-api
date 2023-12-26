package com.products.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.products.config.security.JwtService;
import com.products.dto.auth.AuthLoginDTO;
import com.products.dto.auth.AuthRegisterDTO;
import com.products.dto.auth.AuthRespuestaDTO;
import com.products.entity.Rol;
import com.products.entity.Usuario;
import com.products.error.LogicError;
import com.products.repository.RolRepository;
import com.products.repository.UsuarioRepository;
import com.products.validations.usuario.registro.ValidarRegistroUsuario;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RolRepository rolRepository;

    private final List<ValidarRegistroUsuario> validaciones;

    public AuthRespuestaDTO registrar(@Valid AuthRegisterDTO datos) {
        validaciones.forEach(v-> v.validar(datos));
        
        Usuario usuario = new Usuario(datos);
        Optional<Rol> rol = rolRepository.findByNombre("USUARIO");
        usuario.setPassword(passwordEncoder.encode(datos.password()));
        usuario.setRol(rol.get());

        usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario);
        return new AuthRespuestaDTO(jwtToken);
    }

    @Transactional
    public AuthRespuestaDTO login(@Valid AuthLoginDTO datos) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(datos.correo(), 
            datos.password())
        );
        Usuario usuario = usuarioRepository.findByCorreo(datos.correo()).orElseThrow(()-> new LogicError(null, "Usuario no encontrado."));
        usuario.setUltimoLogin(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        String jwt = jwtService.generateToken(usuario);

        return new AuthRespuestaDTO(jwt);
    }
    
}
