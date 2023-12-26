package com.products.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CategoriaActualizarDTO(
    @Pattern(regexp = "^[a-zA-ZñÑ\\s]{4,50}$", message = "El nombre de la categoría puede tener entre 4 y 50 letras.")
    @NotBlank(message = "El nombre de la categoría es requerido.")
    String nombre
) {
    
}
