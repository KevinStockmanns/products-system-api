package com.products.dto.pedido;

import java.util.List;

import com.products.dto.orden.OrdenRegistroDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PedidoRegistrarDTO(

    @Pattern(regexp = "^[a-záéíóúA-ZÁÉÍÓÚñÑ\\s*]{5,100}$", message = "El nombre debe tener entre 5 y 100 letras.")
    String cliente,

    @Email(message = "El formato del correo no es válido.")
    String correo, 

    @Pattern(regexp = "^\\+\\d{2,3} \\d{3,4} \\d{6,8}$", message = "El formato del telefono no es válido.")
    String telefono,

    @NotNull(message = "Las ordenes son requeridas.")
    @Valid
    @Size(min = 1, message = "Es requerido al menos una orden para el pedido.")
    List<OrdenRegistroDTO> ordenes
) {
    
}
