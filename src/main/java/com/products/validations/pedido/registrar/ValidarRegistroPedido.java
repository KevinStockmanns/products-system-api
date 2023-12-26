package com.products.validations.pedido.registrar;

import org.springframework.security.core.Authentication;

import com.products.dto.pedido.PedidoRegistrarDTO;

public interface ValidarRegistroPedido {
    
    public void validar(PedidoRegistrarDTO datos, Authentication auth);
}
