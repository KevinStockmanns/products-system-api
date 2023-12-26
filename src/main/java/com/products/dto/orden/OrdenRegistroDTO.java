package com.products.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrdenRegistroDTO(
    @NotNull(message = "La versión del id es requerida.")
    Long idVersion,

    @NotNull(message = "La cantidad es requerida.")
    @Min(value = 1, message = "La cantidad mínima es un producto.")
    Integer cantidad,

    @NotNull(message = "El campo withReventa es requerido.")
    Boolean withReventa
) {
}
