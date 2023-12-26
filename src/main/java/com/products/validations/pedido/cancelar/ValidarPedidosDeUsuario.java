package com.products.validations.pedido.cancelar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.entity.Pedido;
import com.products.entity.Usuario;
import com.products.error.LogicError;

@Component
public class ValidarPedidosDeUsuario implements ValidarCancelarPedido {

    @Override
    public void validar(Pedido pedido, Authentication auth) {
        if(auth.getAuthorities().iterator().next().toString().equals("USUARIO")){
            if (pedido.getUsuario() != null && !pedido.getUsuario().equals((Usuario) auth.getPrincipal())){
                throw new LogicError(null, "El pedido no puede ser eliminado por otro usuario");
            }
        }
    }
    
}
