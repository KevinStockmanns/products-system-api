package com.products.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.products.dto.productoVersion.ProductoVersionActualizarDTO;
import com.products.dto.productoVersion.ProductoVersionRegistroDTO;
import com.products.utils.Util;

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
@Table(name = "producto_version")
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductoVersion {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;

    @Column(length = 1500)
    private String descripcion;
    private BigDecimal precio;

    @Column(name = "precio_reventa")
    private BigDecimal precioReventa;
    private Integer stock;
    private Integer vistas;

    @Column(nullable = false)
    private Integer pedidos;
    private Integer ventas;
    private Boolean estado;

    @Column(name = "fecha_lanzamiento")
    private LocalDateTime fechaLanzamiento;

    @ManyToOne
    @JoinColumn(name = "fk_producto")
    private Producto producto;


    public ProductoVersion(ProductoVersionRegistroDTO datos) {
        this.nombre = Util.formatTitle(datos.nombre());
        this.descripcion = Util.formatText(datos.descripcion());
        this.precio = datos.precio().setScale(2, RoundingMode.HALF_UP);
        this.precioReventa = (datos.precioReventa() == null) ? BigDecimal.ZERO : datos.precioReventa().setScale(2, RoundingMode.HALF_UP);
        this.stock = (datos.stock() == null) ? 0 : datos.stock();
        this.vistas = 0;
        this.pedidos = 0;
        this.ventas = 0;
        this.estado = datos.estado() == null ? true : datos.estado();
        this.fechaLanzamiento = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }


    public void aumentarVisita() {
        if(this.estado)
            this.vistas++;
    }


    public void actualizar(ProductoVersionActualizarDTO datos) {
        if(datos.nombre() != null)
            this.nombre = Util.formatTitle(datos.nombre());
        
        if(datos.descripcion() != null)
            this.descripcion = Util.formatText(datos.descripcion());

        if(datos.estado() != null)
            this.estado = datos.estado();

        if(datos.precio() != null)
            this.precio = datos.precio().setScale(2, RoundingMode.HALF_UP);
        
        if(datos.precioReventa() != null)
            this.precioReventa = datos.precioReventa().setScale(2, RoundingMode.HALF_UP);
        
        if(datos.stock() != null)
            this.stock = datos.stock();
    }


    public void setPrecio(BigDecimal precio){
        this.precio = precio.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPrecioReventa(BigDecimal precioReventa){
        this.precio = precioReventa.setScale(2, RoundingMode.HALF_UP);
    }


    public void aumentarVenta(Integer cantidad) {
        this.ventas += cantidad;
    }


    public void disminuirStock(Integer cantidad) {
        int nuevoStock = this.stock - cantidad;

        this.stock = (nuevoStock > 0) ? nuevoStock : 0;
    }


    public void aumentarPedidos(Integer cantidad) {
        this.pedidos += cantidad;
    }


    public void disminuirPedidos(Integer cantidad) {
        int nuevoPedido = this.pedidos - cantidad;

        this.pedidos = (nuevoPedido > 0) ? nuevoPedido : 0;
    }
}
