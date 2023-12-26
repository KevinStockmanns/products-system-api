package com.products.validations.producto.actualizar;

import org.springframework.stereotype.Component;

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.dto.productoVersion.ProductoVersionActualizarDTO;
import com.products.entity.Producto;
import com.products.entity.ProductoVersion;
import com.products.error.LogicError;

@Component
public class ValidarProductoVersionId implements ValidarActualizarProducto{
    
    @Override
    public void validar(ProductoActualizarDTO datos, Producto producto) {
        if(datos.versiones() != null){
            int i = 0;
            for(ProductoVersionActualizarDTO verDTO: datos.versiones()){
                boolean idEncontrado = false;
                for(ProductoVersion pv : producto.getVersiones()){
                    if(pv.getId().equals(verDTO.id()))
                        idEncontrado = true;
                    }
                    
                if(!idEncontrado)
                    throw new LogicError("versiones[" + i + "].id", "El id de la versión ingresada no coincide con ninguna versión del producto.");
                i++;
                }
        }
    }
    
}
