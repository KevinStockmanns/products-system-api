package com.products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.auth.AuthLoginDTO;
import com.products.dto.auth.AuthRegisterDTO;
import com.products.dto.auth.AuthRespuestaDTO;
import com.products.service.AuthService;
import com.products.utils.payload.ResponseWrapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<AuthRespuestaDTO>> login(
        @RequestBody @Valid AuthLoginDTO datos
    ){
        AuthRespuestaDTO res = authService.login(datos);
        return ResponseEntity.ok().body(new ResponseWrapper<AuthRespuestaDTO>(
            HttpStatus.OK, 
            "Ingreso con éxito.", 
            null, 
            res));
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<AuthRespuestaDTO>> register(
        @RequestBody @Valid AuthRegisterDTO datos
    ){
        AuthRespuestaDTO res = authService.registrar(datos);
        return ResponseEntity.ok().body(new ResponseWrapper<AuthRespuestaDTO>(
            HttpStatus.OK, 
            "Usuario registrado con éxito.", 
            null, 
            res));
    }
}
