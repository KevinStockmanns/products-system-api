package com.products.validations.pedido.actualizar;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;
import com.products.error.LogicError;
import com.products.utils.enums.EstadoPedido;

@Component
public class ValidarEstadoDistintoVendido implements ValidarActualizarPedido {

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(pedido.getEstado().equals(EstadoPedido.VENDIDO))
            throw new LogicError("estado", "Para editar el pedido el estado debe ser distinto de 'vendido'");
    }
    
}
