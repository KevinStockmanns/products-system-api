package com.products.dto.productoVersion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.products.entity.ProductoVersion;

public record ProductoVersionRespuestaDTO(
    Long id,
    String nombre,
    String descripcion, 
    Boolean estado,
    BigDecimal precio,
    BigDecimal precioReventa,
    Integer stock,
    LocalDateTime fechaLanzamiento
) {
    public ProductoVersionRespuestaDTO(ProductoVersion pVersion){
        this(
            pVersion.getId(), 
            pVersion.getNombre(), 
            pVersion.getDescripcion(), 
            pVersion.getEstado(),
            pVersion.getPrecio(),
            pVersion.getPrecioReventa(),
            pVersion.getStock(),
            LocalDateTime.ofInstant(pVersion.getFechaLanzamiento().toInstant(ZoneOffset.UTC), ZoneId.of("UTC-3")));
    }
}
