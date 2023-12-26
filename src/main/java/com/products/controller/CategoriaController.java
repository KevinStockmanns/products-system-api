package com.products.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.categoria.CategoriaActualizarDTO;
import com.products.dto.categoria.CategoriaRespuestaDTO;
import com.products.entity.Categoria;
import com.products.service.CategoriaService;
import com.products.utils.payload.ResponseWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<ResponseWrapper<Page<CategoriaRespuestaDTO>>> listarCategorias(){

        Page<Categoria> categorias = categoriaService.listar(Pageable.unpaged());
        Page<CategoriaRespuestaDTO> categoriasDTO = categorias.map(CategoriaRespuestaDTO::new);
        return ResponseEntity.ok().body(new ResponseWrapper<Page<CategoriaRespuestaDTO>>(
            HttpStatus.OK, 
            "listado de categorías exitoso.", 
            null, 
            categoriasDTO));
    }

    @PutMapping("categoria/{id}")
    private ResponseEntity<?> actualizarCategoria(
        @PathVariable Long id,
        @RequestBody @Valid CategoriaActualizarDTO datos
    ){
        Categoria categoria = categoriaService.actualizar(id, datos);
        return ResponseEntity.ok().body(new ResponseWrapper<CategoriaRespuestaDTO>(
            HttpStatus.OK, 
            "Categoría actualizada exitosamente.", 
            null, 
            new CategoriaRespuestaDTO(categoria)));
    }
}
