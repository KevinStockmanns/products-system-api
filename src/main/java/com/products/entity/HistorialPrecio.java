package com.products.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

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
@Table(name = "historial_precios")
@Data @AllArgsConstructor @NoArgsConstructor
public class HistorialPrecio {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_producto_version")
    private ProductoVersion version;

    @Column(name = "fecha_cambio")
    private LocalDate fechaCambio;

    private BigDecimal precio;

    @Column(name = "precio_reventa")
    private BigDecimal precioReventa;


    public HistorialPrecio(ProductoVersion pVersionSinAumento) {
        this.version = pVersionSinAumento;
        this.fechaCambio = LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC);
        this.precio = pVersionSinAumento.getPrecio();
        this.precioReventa = pVersionSinAumento.getPrecioReventa();
    }
}
