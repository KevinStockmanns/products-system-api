package com.products.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.dto.pedido.PedidoRegistrarDTO;
import com.products.utils.Util;
import com.products.utils.enums.EstadoPedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data @AllArgsConstructor @NoArgsConstructor
public class Pedido {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Column(name = "fecha_confirmado")
    private LocalDateTime fechaConfirmado;
    
    private String cliente;
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoPedido estado;
    
    @OneToMany(mappedBy = "pedido")
    private List<Orden> ordenes;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    public Pedido(@Valid PedidoRegistrarDTO datos, Usuario usuario) {
        this.fecha = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        if(usuario == null){
            this.cliente = datos.cliente();
            this.correo = datos.correo();
            this.telefono = datos.telefono();
            this.usuario = null;
        }else{
            this.usuario = usuario;
        }
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void actualizar(@Valid PedidoActualizarDTO datos) {
        if(datos.cliente() != null)
            this.cliente = Util.formatTitle(datos.cliente());
        
        if(datos.correo() != null)
            this.correo = datos.correo();

        if(datos.telefono() != null)
            this.telefono = datos.telefono();
        
        if(datos.estado() != null)
            this.estado = EstadoPedido.valueOf(datos.estado().toUpperCase());
    }

    public void agregarPedido(Orden orden) {
        this.ordenes.add(orden);
    }
    
}