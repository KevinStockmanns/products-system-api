package com.products.entity;

import java.math.BigDecimal;

import com.products.dto.orden.OrdenActualizarDTO;
import com.products.dto.orden.OrdenRegistroDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenes")
@Data @AllArgsConstructor @NoArgsConstructor
public class Orden {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_producto_version")
    private ProductoVersion version;
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido pedido;


    public Orden(OrdenRegistroDTO datos, ProductoVersion pVersion) {
        this.cantidad = datos.cantidad();
        this.precioUnitario = datos.withReventa() ? pVersion.getPrecioReventa() : pVersion.getPrecio();
        this.version = pVersion;
    }


    public void actualizar(OrdenActualizarDTO datos) {
        if(datos.precioUnitario() != null){
            this.precioUnitario = datos.precioUnitario();
        }
        if(datos.cantidad() != null){
            this.cantidad = datos.cantidad();
        }
    }


    public Orden(OrdenActualizarDTO datos, ProductoVersion pVersion) {
        this.cantidad = datos.cantidad();
        this.precioUnitario = (datos.precioUnitario() == null) ? pVersion.getPrecio() : datos.precioUnitario();
        this.version = pVersion;
    }
}
