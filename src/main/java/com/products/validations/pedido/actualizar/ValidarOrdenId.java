package com.products.validations.pedido.actualizar;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.orden.OrdenActualizarDTO;
import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Orden;
import com.products.entity.Pedido;
import com.products.error.LogicError;

@Component
public class ValidarOrdenId implements ValidarActualizarPedido {

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(datos.ordenes() != null){
            List<OrdenActualizarDTO> ordenesDTO = datos.ordenes();
            for(int i = 0; i < ordenesDTO.size(); i++){
                OrdenActualizarDTO ordenDto = ordenesDTO.get(i);
                if(ordenDto.idOrden() != null && !ordenDto.agregar()){
                    boolean idEncontrado = false;
                    for(Orden o : pedido.getOrdenes()){
                        if(ordenDto.idOrden() == o.getId())
                            idEncontrado = true;
                    }
                    if(!idEncontrado)
                        throw new LogicError("ordenes["+i+"].idOrden", "El id de la orden no corresponde con las ordenes del pedido.");
                }
            }
        }
    }
    
}
