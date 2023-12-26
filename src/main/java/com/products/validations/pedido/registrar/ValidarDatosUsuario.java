package com.products.validations.pedido.registrar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.pedido.PedidoRegistrarDTO;
import com.products.error.LogicError;

@Component
public class ValidarDatosUsuario implements ValidarRegistroPedido {

    @Override
    public void validar(PedidoRegistrarDTO datos, Authentication auth) {
        if(auth == null && (datos.cliente() == null || datos.correo() == null || datos.telefono() == null)){
            throw new LogicError(null, "Son requeridos los datos personales.");
        }
    }
    
}
