package com.products.dto.usuario;

import com.products.entity.Usuario;

public record UsuarioRespuestaDTO(
    Long id,
    String nombre, 
    String apellido,
    String correo,
    String telefono,
    String rol
) {
    public UsuarioRespuestaDTO(Usuario usuario){
        this(usuario.getId(), 
        usuario.getNombre(), 
        usuario.getApellido(), 
        usuario.getCorreo(), 
        usuario.getTelefono(),
        usuario.getRol().getNombre());
    }
}
