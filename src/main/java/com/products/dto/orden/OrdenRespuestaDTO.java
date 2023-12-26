package com.products.dto.orden;

import java.math.BigDecimal;

import com.products.dto.productoVersion.ProductoVersionRespuestaDTO;
import com.products.entity.Orden;

public record OrdenRespuestaDTO(
    Long id,
    ProductoVersionRespuestaDTO version,
    Integer cantidad,
    BigDecimal precioUnitario
) {
    public OrdenRespuestaDTO(Orden orden){
        this(orden.getId(), 
        new ProductoVersionRespuestaDTO(orden.getVersion()), 
        orden.getCantidad(), 
        orden.getPrecioUnitario());
    }
    
}
