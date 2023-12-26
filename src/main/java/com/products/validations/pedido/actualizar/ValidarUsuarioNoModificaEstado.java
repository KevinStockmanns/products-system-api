package com.products.validations.pedido.actualizar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;
import com.products.error.LogicError;

@Component
public class ValidarUsuarioNoModificaEstado implements ValidarActualizarPedido{

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(auth.getAuthorities().iterator().next().toString().equals("USUARIO")){
            if(datos.estado() != null){
                throw new LogicError("estado", "No puedes modificar el estado del pedido");
            }
        }
    }
    
}
