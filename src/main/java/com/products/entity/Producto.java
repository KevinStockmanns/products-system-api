package com.products.entity;

import java.util.List;

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.dto.producto.ProductoRegistroDTO;
import com.products.utils.Util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data @AllArgsConstructor @NoArgsConstructor
public class Producto {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private Boolean estado;

    @OneToMany(mappedBy = "producto")
    private List<ProductoVersion> versiones;
    
    @ManyToMany
    private List<Categoria> categorias;


    public Producto(@Valid ProductoRegistroDTO datos) {
        this.nombre = Util.formatTitle(datos.nombre());
        this.estado = datos.versiones().stream().anyMatch(pv-> pv.estado() == null || pv.estado());
    }


    public void aumentarVisita() {
     for (ProductoVersion version : this.getVersiones()) {
        version.aumentarVisita();
     }
    }


    public void actualizar(@Valid ProductoActualizarDTO datos) {
        if(datos.nombre() != null)
            this.nombre = Util.formatTitle(datos.nombre());
        
        if(datos.estado() != null){
            this.estado = datos.estado();
        }
    }

    private Boolean isOneVersionEnable() {
        return this.versiones.stream().anyMatch(v-> v.getEstado());
    }

    public void revisarEstado() {
        if(this.estado){
            if(!this.isOneVersionEnable()){
                this.estado = false;
            }
        }
    }


    public void desactivar() {
        this.estado = false;
    }
}
