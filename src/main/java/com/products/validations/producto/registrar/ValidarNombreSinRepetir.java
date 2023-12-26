package com.products.validations.producto.registrar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.products.dto.producto.ProductoRegistroDTO;
import com.products.error.LogicError;
import com.products.repository.ProductoRepository;

@Component
public class ValidarNombreSinRepetir implements ValidarRegistroProducto{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void validar(ProductoRegistroDTO datos) {
        if(productoRepository.existsByNombre(datos.nombre())){
            throw new LogicError("nombre", "El nombre '" + datos.nombre() + "' ya está en uso.");
        }
    }
    
}
