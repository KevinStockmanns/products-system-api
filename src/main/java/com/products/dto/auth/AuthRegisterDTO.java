package com.products.dto.auth;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record AuthRegisterDTO(
    @NotBlank(message = "El nombre es requerido.")
    String nombre,

    @NotBlank(message = "El apellido es requerido.")
    String apellido,

    @NotBlank(message = "El telefono es requerido.")
    String telefono,

    @NotBlank(message = "El correo es requerido.")
    @Email(message = "El formato del correo no es válido.")
    String correo,

    @NotNull(message = "La fecha de nacimiento es requerida.")
    @DateTimeFormat(iso = ISO.DATE)
    @Past(message = "La fecha de nacimiento debe ser pasada a la fecha actual.")
    LocalDate fechaNacimiento,

    @NotBlank(message = "La clave es requerida.")
    String password
) {
    
}
