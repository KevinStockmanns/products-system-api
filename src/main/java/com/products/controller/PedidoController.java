package com.products.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.products.dto.pedido.PedidoActualizarDTO;
import com.products.dto.pedido.PedidoRegistrarDTO;
import com.products.dto.pedido.PedidoRespuestaDTO;
import com.products.entity.Pedido;
import com.products.entity.Usuario;
import com.products.error.LogicError;
import com.products.service.PedidoService;
import com.products.utils.enums.EstadoPedido;
import com.products.utils.payload.ResponseWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @PostMapping("/pedido")
    public ResponseEntity<ResponseWrapper<PedidoRespuestaDTO>> hacerPedido(
        @RequestBody @Valid PedidoRegistrarDTO datos,
        UriComponentsBuilder uriComponentsBuilder,
        Authentication auth
    ){
        Pedido pedido = pedidoService.crear(datos, auth);
        URI uri = uriComponentsBuilder.path("pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseWrapper<PedidoRespuestaDTO>(
            HttpStatus.CREATED, 
            "Pedido registrado con éxito.", 
            null, 
            new PedidoRespuestaDTO(pedido)));
    }

    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<Void> cancelarPedido(
        @PathVariable Long id,
        Authentication auth
    ){
        pedidoService.cancelarPedido(id, auth);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/pedido/{id}/confirmar")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<Void> confirmarPedido(@PathVariable Long id){
        pedidoService.confirmar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/pedido/{id}/vender")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<Void> venderPedido(@PathVariable Long id){
        pedidoService.vender(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("pedido/{id}")
    public ResponseEntity<ResponseWrapper<PedidoRespuestaDTO>> actualizarPedido(
        @PathVariable Long id,
        @RequestBody @Valid PedidoActualizarDTO datos,
        Authentication auth
    ){
        Pedido pedido = pedidoService.actualizar(id, datos, auth);
        return ResponseEntity.ok().body(new ResponseWrapper<PedidoRespuestaDTO>(
            HttpStatus.OK, 
            "Pedido actualizado con éxito.", 
            null, 
            new PedidoRespuestaDTO(pedido)));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<ResponseWrapper<Page<PedidoRespuestaDTO>>> listarPedidos(
        @RequestParam(defaultValue = "pendiente") String estado,
        @RequestParam(defaultValue = "ASC") String sortDirection,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        Authentication authentication
    ){
        try{
            EstadoPedido.valueOf(estado.toUpperCase());
        }catch(IllegalArgumentException e){
            throw new LogicError(null, "El parametro 'estado' ingresado no es válido.");
        }
        size = (size > 10 || size < 1) ? 10 : size;
        Direction direction = (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC"))
            ? Direction.ASC
            : (sortDirection.equalsIgnoreCase("ASC")) 
                ? Direction.ASC
                : Direction.DESC;

        String rol = authentication.getAuthorities().iterator().next().toString();
        System.out.println(rol);
        Pageable paginacion = PageRequest.of(page, size, Sort.by(direction, "fecha"));
        Page<Pedido> pedidos = null;
        if(rol.equals("USUARIO"))
            pedidos = pedidoService.listarPorUsuario(paginacion, estado, (Usuario) authentication.getPrincipal());
        else if(rol.equals("SUPERADMIN") || rol.equals("ADMIN"))
            pedidos = pedidoService.listar(paginacion, estado);
        Page<PedidoRespuestaDTO> pedidosDTO = pedidos.map(PedidoRespuestaDTO::new);
        
        return ResponseEntity.ok().body(new ResponseWrapper<Page<PedidoRespuestaDTO>>(
            HttpStatus.OK, 
            "Listado de pedidos exitoso.", 
            null, 
            pedidosDTO));
    }
}
