package com.products.validations.pedido.actualizar;

import org.springframework.security.core.Authentication;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;

public interface ValidarActualizarPedido {
    
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth);
}
