package com.products.validations.pedido.actualizar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;
import com.products.error.LogicError;
import com.products.utils.enums.EstadoPedido;


@Component
public class ValidarEstado implements ValidarActualizarPedido{

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(datos.estado() != null){
            try{
                EstadoPedido.valueOf(datos.estado().toUpperCase());
            }catch( IllegalArgumentException e){
                throw new LogicError("estado", "El estado ingresado no es una opción válida.");
            }
        }
    }
    
}
