package com.products.validations.pedido.cancelar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.entity.Pedido;
import com.products.error.LogicError;
import com.products.utils.enums.EstadoPedido;

@Component
public class ValidarEstadoNoVendido implements ValidarCancelarPedido{

    @Override
    public void validar(Pedido pedido, Authentication auth) {
        if(auth.getAuthorities().iterator().next().toString().equals("USUARIO")){
            if(pedido.getEstado().equals(EstadoPedido.VENDIDO))
                throw new LogicError(null, "No puedes cancelar un pedido que ha sido vendido.");
            if(pedido.getEstado().equals(EstadoPedido.CANCELADO))
                throw new LogicError(null, "No puedes cancelar un pedido que ya ha sido cancelado.");
        }
    }
    
}
