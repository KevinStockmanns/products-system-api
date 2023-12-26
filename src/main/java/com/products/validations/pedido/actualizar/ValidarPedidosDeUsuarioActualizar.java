package com.products.validations.pedido.actualizar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;
import com.products.entity.Usuario;
import com.products.error.LogicError;

@Component
public class ValidarPedidosDeUsuarioActualizar implements ValidarActualizarPedido{

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(auth.getAuthorities().iterator().next().toString().equals("USUARIO")){
            if((pedido.getUsuario() != null && !pedido.getUsuario().equals((Usuario) auth.getPrincipal()))
            || pedido.getUsuario() == null)
                throw new LogicError(null, "Solo puedes editar pedidos que haz realizado con esta cuenta.");
        }
    }
    
}
