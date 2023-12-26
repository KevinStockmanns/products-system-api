package com.products.dto.productoVersion;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ProductoVersionCambiarPrecioDTO(
    @NotNull(message = "El id de la versión del producto es requerido.")
    Long id,

    @NotNull(message = "El precio es requerido.")
    @DecimalMin(value = "0.01", message = "El precio debe ser positivo.")
    BigDecimal precio,

    @NotNull(message = "El precio de reventa es requerido.")
    @DecimalMin(value = "0.01", message = "El precio de reventa debe ser positivo.")
    BigDecimal precioReventa
) {
    
}
