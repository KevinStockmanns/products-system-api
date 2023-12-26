package com.products.dto.categoria;

import com.products.entity.Categoria;

public record CategoriaRespuestaDTO(
    Long id,
    String nombre
) {
    public CategoriaRespuestaDTO(Categoria categoria){
        this(categoria.getId(), categoria.getNombre());
    }
}
