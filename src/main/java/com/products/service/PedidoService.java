package com.products.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.products.dto.orden.OrdenActualizarDTO;
import com.products.dto.orden.OrdenRegistroDTO;
import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.dto.pedido.PedidoRegistrarDTO;
import com.products.entity.Orden;
import com.products.entity.Pedido;
import com.products.entity.ProductoVersion;
import com.products.entity.Usuario;
import com.products.repository.OrdenRepository;
import com.products.repository.PedidoRepository;
import com.products.repository.ProductoVersionRepository;
import com.products.repository.UsuarioRepository;
import com.products.utils.enums.EstadoPedido;
import com.products.validations.pedido.actualizar.ValidarActualizarPedido;
import com.products.validations.pedido.cancelar.ValidarCancelarPedido;
import com.products.validations.pedido.registrar.ValidarRegistroPedido;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PedidoService {

    @Autowired
    private ProductoVersionRepository productoVersionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private List<ValidarRegistroPedido> validacionesRegistrarPedido;

    @Autowired
    private List<ValidarActualizarPedido> validacionesActualizarPedido;

    @Autowired
    private List<ValidarCancelarPedido> validarCancelarPedidos;

    @Transactional
    public Pedido crear(@Valid PedidoRegistrarDTO datos, Authentication auth) {
        validacionesRegistrarPedido.forEach(v-> v.validar(datos, auth));

        Usuario usuario = auth == null ? null : (Usuario) auth.getPrincipal();
        Pedido pedido = new Pedido(datos, usuario);
        List<Orden> ordenes = new ArrayList<>();
        for(OrdenRegistroDTO ordenDTO : datos.ordenes()){
            ProductoVersion pVersion = productoVersionRepository.getReferenceById(ordenDTO.idVersion());
            Orden orden = new Orden(ordenDTO, pVersion);
            pVersion.aumentarPedidos(orden.getCantidad());
            orden.setPedido(pedido);
            ordenes.add(orden);
        }

        pedido.setOrdenes(ordenes);
        pedido = pedidoRepository.save(pedido);
        ordenes = ordenRepository.saveAll(ordenes);
        
        return pedido;
    }

    @Transactional
    public void cancelarPedido(Long id, Authentication auth) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        validarCancelarPedidos.forEach(v-> v.validar(pedido, auth));
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedido.setFechaConfirmado(null);
    }

    @Transactional
    public void vender(Long id) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.getOrdenes().forEach(orden->{
            ProductoVersion pVersion = orden.getVersion();
            pVersion.aumentarVenta(orden.getCantidad());
            pVersion.disminuirStock(orden.getCantidad());
        });
        pedido.setEstado(EstadoPedido.VENDIDO);
    }

    @Transactional
    public void confirmar(Long id) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.setEstado(EstadoPedido.CONFIRMADO);
        pedido.setFechaConfirmado(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    }

    @Transactional
    public Pedido actualizar(Long id, @Valid PedidoActualizarDTO datos, Authentication auth) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        final Pedido pedidoFinal =pedido;
        validacionesActualizarPedido.forEach(v-> v.validar(datos, pedidoFinal, auth));
        pedido.actualizar(datos);

        if(datos.ordenes() != null){
            for(OrdenActualizarDTO ordenDTO: datos.ordenes()){
                if(ordenDTO.eliminar()){
                    Orden orden = ordenRepository.getReferenceById(ordenDTO.idOrden());
                    orden.getVersion().disminuirPedidos(orden.getCantidad());
                    ordenRepository.delete(orden);

                }else if(ordenDTO.agregar()){
                    ProductoVersion pVersion = productoVersionRepository.getReferenceById(ordenDTO.idVersion());
                    pVersion.aumentarPedidos(ordenDTO.cantidad());
                    Orden orden = new Orden(ordenDTO, pVersion);
                    pedido.agregarPedido(orden);
                    orden.setPedido(pedido);
                    ordenRepository.save(orden);

                }else if(!ordenDTO.eliminar() && !ordenDTO.agregar()){
                    Orden orden = ordenRepository.getReferenceById(id);
                    orden.actualizar(ordenDTO);
                }
            }
        
        }
        return pedido;
    }

    public Page<Pedido> listar(Pageable paginacion, String estado) {
        
        return pedidoRepository.findByEstado(paginacion, EstadoPedido.valueOf(estado.toUpperCase()));
    }

    public Page<Pedido> listarPorUsuario(Pageable paginacion, String estado, Usuario usuario) {
        return pedidoRepository.findByEstadoAndUsuario(paginacion, EstadoPedido.valueOf(estado.toUpperCase()), usuario);
    }
    
}
