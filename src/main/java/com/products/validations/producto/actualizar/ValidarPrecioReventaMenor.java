package com.products.validations.producto.actualizar;

import org.springframework.stereotype.Component;

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.dto.productoVersion.ProductoVersionActualizarDTO;
import com.products.entity.Producto;
import com.products.error.LogicError;

@Component
public class ValidarPrecioReventaMenor implements ValidarActualizarProducto {

    @Override
    public void validar(ProductoActualizarDTO datos, Producto producto) {
        if(datos.versiones() != null){
            for(int i = 0; i < datos.versiones().size(); i++){
                ProductoVersionActualizarDTO verDTO = datos.versiones().get(i);
                if (verDTO.precio() == null && verDTO.precioReventa() == null) {
                    continue;
                } else if (verDTO.precio() == null || verDTO.precioReventa() == null) {
                    if (verDTO.precio() == null) {
                        throw new LogicError("versiones[" + i + "].precio", "El precio es necesario.");
                    } else {
                        throw new LogicError("versiones[" + i + "].precioReventa", "El precio de reventa es necesario.");
                    }
                } else if (verDTO.precioReventa().compareTo(verDTO.precio()) > 0) {
                    throw new LogicError("versiones[" + i + "].precioReventa", "El precio de reventa debe ser menor que el precio original.");
                }
            }
        }
    }
    
}
