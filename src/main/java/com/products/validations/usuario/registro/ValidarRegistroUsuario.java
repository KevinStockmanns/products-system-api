package com.products.validations.usuario.registro;

import com.products.dto.auth.AuthRegisterDTO;

public interface ValidarRegistroUsuario {
    
    public void validar(AuthRegisterDTO datos);
}
