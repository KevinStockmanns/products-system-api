package com.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.products.dto.categoria.CategoriaActualizarDTO;
import com.products.entity.Categoria;
import com.products.error.LogicError;
import com.products.repository.CategoriaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<Categoria> listar(Pageable paginacion) {
        Page<Categoria> categorias = categoriaRepository.findAll(paginacion);

        return categorias;
    }

    @Transactional
    public Categoria actualizar(Long id, @Valid CategoriaActualizarDTO datos) {
        Categoria categoria = categoriaRepository.getReferenceById(id);
        if(categoriaRepository.existsByNombre(datos.nombre()))
            throw new LogicError("nombre", "El nombre ingresado ya está en uso");
        
        categoria.actualizar(datos);
        return categoria;
    }
    
}
