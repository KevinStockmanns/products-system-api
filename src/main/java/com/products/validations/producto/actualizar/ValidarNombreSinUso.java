package com.products.validations.producto.actualizar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.entity.Producto;
import com.products.error.LogicError;
import com.products.repository.ProductoRepository;

@Component
public class ValidarNombreSinUso implements ValidarActualizarProducto{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void validar(ProductoActualizarDTO datos, Producto producto) {
        if(datos.nombre() != null)
            if(productoRepository.existsByNombre(datos.nombre()))
                throw new LogicError("nombre", "El nombre '" + datos.nombre() + "' ya está en uso");
    }
    
}
