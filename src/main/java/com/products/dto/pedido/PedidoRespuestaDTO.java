package com.products.dto.pedido;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import com.products.dto.orden.OrdenRespuestaDTO;
import com.products.dto.usuario.UsuarioRespuestaDTO;
import com.products.entity.Pedido;

public record PedidoRespuestaDTO(
    Long id,
    LocalDateTime fecha,
    String cliente, 
    String correo,
    String telefono,
    String estado,
    List<OrdenRespuestaDTO> ordenes,
    UsuarioRespuestaDTO usuario
) {

    public PedidoRespuestaDTO(Pedido pedido) {
        this(pedido.getId(), 
        LocalDateTime.ofInstant(pedido.getFecha().toInstant(ZoneOffset.UTC), ZoneId.of("UTC-3")), 
        pedido.getUsuario() == null ? pedido.getCliente() : null, 
        pedido.getUsuario() == null ? pedido.getCorreo() : null, 
        pedido.getUsuario() == null ? pedido.getTelefono() : null,
        pedido.getEstado().toString(),
        pedido.getOrdenes().stream().map(OrdenRespuestaDTO::new).toList(), 
        pedido.getUsuario() != null ? new UsuarioRespuestaDTO(pedido.getUsuario()) : null);
    }
    
}
