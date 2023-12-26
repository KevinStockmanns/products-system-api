package com.products.dto.productoVersion;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProductoVersionActualizarDTO(
    @NotNull(message = "El id de la versión del producto es requerido.")
    Long id,

    @Pattern(regexp = "^[a-zA-ZñÑ0-9\\s]{4,50}$", message = "El nombre debe tener entre 4 y 50 caracteres.")
    String nombre, 

    @Pattern(regexp = "^.{15,1000}$", message = "La descripción del mensaje debe tener entra 15 y 1000 caracteres")
    String descripcion,

    @DecimalMin(value = "0.01", message = "El precio de la versión del producto debe ser positiva.")
    BigDecimal precio,

    @DecimalMin(value = "0.01", message = "El precio de reventa de la versión del producto debe ser positiva.")
    BigDecimal precioReventa,

    @Min(value = 0, message = "El valor mínimo aceptado para el stock es 0.")
    Integer stock,
    Boolean estado

) {
    
}
