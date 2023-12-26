package com.products.validations.pedido.actualizar;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.products.dto.orden.OrdenActualizarDTO;
import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.entity.Pedido;
import com.products.error.LogicError;

@Component
public class ValidarOrdenIntegridad implements ValidarActualizarPedido{

    @Override
    public void validar(PedidoActualizarDTO datos, Pedido pedido, Authentication auth) {
        if(datos.ordenes() != null){
            List<OrdenActualizarDTO> ordenesDTO = datos.ordenes();
            for(int i = 0; i < ordenesDTO.size(); i++){
                OrdenActualizarDTO ordenDTO = ordenesDTO.get(i);
                if(ordenDTO.agregar() && ordenDTO.eliminar()){
                    throw new LogicError(null, "El campo 'agregar' y 'eliminar' no pueden ser 'true' a la vez.");
                }else if(!ordenDTO.agregar() && !ordenDTO.eliminar() && ordenDTO.idOrden() == null){
                    if(ordenDTO.cantidad() == null && ordenDTO.precioUnitario() == null){
                        throw new LogicError(null, "Para actualizar es requerido la cantidad o el precio.");
                    }
                    throw new LogicError("ordenes["+i+"].idOrden", "Para actualizar es requerido el id de la orden.");
                }else if(ordenDTO.eliminar() && ordenDTO.idOrden() == null){
                    throw new LogicError("ordenes["+i+"].idOrden", "Para eliminar una orden es requerido el id de la orden.");
                }else if(ordenDTO.agregar() && ordenDTO.idVersion() == null){
                    if(ordenDTO.cantidad() == null)
                        throw new LogicError("ordenes["+i+"].cantidad", "Para agregar una orden se necesita la cantidad.");
                    throw new LogicError("ordenes["+i+"].idVersion", "Para agregar una orden es necesario el id de la versión del producto.");
                }
                
            }
        }else{
            if(datos.cliente() == null && datos.correo() == null && datos.telefono() == null && datos.estado() == null)
                throw new LogicError(null, "El cuerpo no debe estar vacío.");
        }
        
    }
    
}
