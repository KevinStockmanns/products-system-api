package com.products.dto.pedido;

import java.util.List;

import com.products.dto.orden.OrdenActualizarDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PedidoActualizarDTO(
    @Pattern(regexp = "^[a-záéíóúA-ZÁÉÍÓÚñÑ\\s*]{5,100}$", message = "El nombre debe tener entre 5 y 100 letras.")
    String cliente,

    @Email(message = "El formato del correo no es válido.")
    String correo,

    @Pattern(regexp = "^\\+\\d{2,3} \\d{3,4} \\d{6,8}$", message = "El formato del telefono no es válido.")
    String telefono,
    String estado,

    @Valid
    @Size(min = 1, message = "Al menos una orden es requerida.")
    List<OrdenActualizarDTO> ordenes
) {
    
}
