package com.products.dto.orden;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrdenActualizarDTO(
    Long idOrden,
    Long idVersion,

    @Min(value = 1, message = "La cantidad mínima es 1.")
    Integer cantidad,

    @DecimalMin(value = "0.01", message = "El precio debe ser positivo.")
    BigDecimal precioUnitario,

    @NotNull(message = "El campo 'eliminar' es requerido.")
    boolean eliminar,

    @NotNull(message = "El campo 'agregar' es requerido.")
    boolean agregar
) {
    
}
