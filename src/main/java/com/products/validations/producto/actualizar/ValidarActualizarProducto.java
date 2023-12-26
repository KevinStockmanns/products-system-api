package com.products.validations.producto.actualizar;

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.entity.Producto;

public interface ValidarActualizarProducto {
    
    public void validar(ProductoActualizarDTO datos, Producto producto);
}
