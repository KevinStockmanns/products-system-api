package com.products.entity;

import com.products.dto.categoria.CategoriaActualizarDTO;
import com.products.dto.categoria.CategoriaRegistroDTO;
import com.products.utils.Util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data @AllArgsConstructor @NoArgsConstructor
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


    public Categoria(CategoriaRegistroDTO datos) {
        this.nombre = Util.formatTitle(datos.nombre());
    }


    public Categoria(String nombre) {
        this.nombre = Util.formatTitle(nombre);
    }


    public void actualizar(@Valid CategoriaActualizarDTO datos) {
        this.nombre = datos.nombre();
    }
}
