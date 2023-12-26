package com.products.validations.pedido.cancelar;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.entity.Pedido;
import com.products.error.LogicError;

@Component
public class ValidarTiempoParaCancelar implements ValidarCancelarPedido{

    private final static Duration TIEMPO_MAX_CANCELACION = Duration.ofHours(24);

    @Override
    public void validar(Pedido pedido, Authentication auth) {
        if(pedido.getFechaConfirmado() != null){
            Instant fehcaConfirmado = pedido.getFechaConfirmado().toInstant(ZoneOffset.UTC);
            System.out.println(fehcaConfirmado);

            Duration tiempoTranscurrido = Duration.between(fehcaConfirmado, Instant.now());

            if(tiempoTranscurrido.compareTo(TIEMPO_MAX_CANCELACION) >= 0)
                throw new LogicError(null, "El período de cancelación de un pedido confirmado ha terminado.");
        }
    }

}
