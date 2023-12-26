package com.products.dto.producto;

import java.util.List;

import com.products.dto.categoria.CategoriaRegistroDTO;
import com.products.dto.productoVersion.ProductoVersionRegistroDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoRegistroDTO(
    @NotBlank(message = "El nombre del producto es requerido.")
    @Pattern(regexp = "^[a-zA-ZñÑ0-9\\s]{4,50}$", message = "El nombre del producto puede tener entre 4 y 50 caracteres.")
    String nombre,

    @Valid
    @NotNull(message = "Las versiones del producto son requeridas.")
    @Size(min = 1, message = "Se necesita al menos una versión del producto.")
    List<ProductoVersionRegistroDTO> versiones,

    @Valid
    @Size(min = 1, message = "Se requiere al menos una categoría para este producto.")
    List<CategoriaRegistroDTO> categorias
) {
    
}
