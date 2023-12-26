package com.products.dto.producto;

import java.util.List;

import com.products.dto.categoria.CategoriaRespuestaDTO;
import com.products.dto.productoVersion.ProductoVersionRespuestaDTO;
import com.products.entity.Producto;

public record ProductoRespuestaDTO(
    Long id,
    String nombre,
    Boolean estado,
    List<ProductoVersionRespuestaDTO> versiones,
    List<CategoriaRespuestaDTO> categorias
) {

    public ProductoRespuestaDTO(Producto producto) {
        this(producto.getId(),
        producto.getNombre(), 
        producto.getEstado(),
        producto.getVersiones().stream().map(ProductoVersionRespuestaDTO::new).toList(),
        producto.getCategorias() == null ? null : producto.getCategorias().stream().map(CategoriaRespuestaDTO::new).toList());
    }
    
}
