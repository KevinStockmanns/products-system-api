package com.products.validations.producto.registrar;

import org.springframework.stereotype.Component;

import com.products.dto.producto.ProductoRegistroDTO;
import com.products.dto.productoVersion.ProductoVersionRegistroDTO;
import com.products.error.IntegrityError;

@Component
public class PrecioReventaMenor implements ValidarRegistroProducto{

    @Override
    public void validar(ProductoRegistroDTO datos) {
        int i = 0;
        for(ProductoVersionRegistroDTO pv: datos.versiones()){
            if(pv.precioReventa() != null){
                if(pv.precioReventa().compareTo(pv.precio()) > 0){
                    throw new IntegrityError("versiones["+i+"].precioReventa", "El precio de reventa debe ser menor que el precio oroginal.");
                }
            }
            i++;
        }
    }
    
}
