package com.products.validations.pedido.cancelar;

import org.springframework.security.core.Authentication;

import com.products.entity.Pedido;

public interface ValidarCancelarPedido {
    public void validar(Pedido pedido, Authentication auth);
}
