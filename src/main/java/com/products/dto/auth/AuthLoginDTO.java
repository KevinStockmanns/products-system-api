package com.products.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
    @NotBlank(message = "El correo es requerido.")
    @Email(message = "El formato del correo no es válido.")
    String correo,

    @NotBlank(message = "La clave es requerida.")
    String password
) {
    
}
