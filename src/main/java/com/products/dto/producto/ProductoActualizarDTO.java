package com.products.dto.producto;

import java.util.List;

import com.products.dto.categoria.CategoriaActualizarDTO;
import com.products.dto.productoVersion.ProductoVersionActualizarDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoActualizarDTO(
    @Pattern(regexp = "^[a-zA-ZñÑ0-9\\s]{4,50}$", message = "El nombre del producto puede tener entre 4 y 50 caracteres.")
    String nombre,
    Boolean estado,

    @Valid
    @Size(min = 1, message = "Al menos una versión del producto es requerido.")
    List<ProductoVersionActualizarDTO> versiones,

    @Valid
    @Size(min = 1, message = "Al menos una categoría del producto es requerido.")
    List<CategoriaActualizarDTO> categorias
) {
    
}
