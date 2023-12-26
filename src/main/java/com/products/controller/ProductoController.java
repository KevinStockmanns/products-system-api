package com.products.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.products.dto.producto.ProductoActualizarDTO;
import com.products.dto.producto.ProductoRegistroDTO;
import com.products.dto.producto.ProductoRespuestaDTO;
import com.products.dto.productoVersion.ProductoVersionCambiarPrecioDTO;
import com.products.entity.Producto;
import com.products.error.IntegrityError;
import com.products.service.ProductoService;
import com.products.utils.payload.ResponseWrapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @PostMapping("/producto")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<ResponseWrapper<ProductoRespuestaDTO>> crearProducto(
        UriComponentsBuilder uriComponentsBuilder,
        @RequestBody @Valid ProductoRegistroDTO datos
    ){
        Producto producto = productoService.crear(datos);

        URI uri = uriComponentsBuilder.path("producto/{id}").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseWrapper<ProductoRespuestaDTO>(
            HttpStatus.CREATED, 
            "Producto Registrado con éxito", 
            null, 
            new ProductoRespuestaDTO(producto)));
    }

    @GetMapping("/productos")
    public ResponseEntity<ResponseWrapper<Page<ProductoRespuestaDTO>>> listarProductos(
        @RequestParam(defaultValue = "true") Boolean activo,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "nombre") String sort,
        @RequestParam(defaultValue = "ASC") String sortDirection
    ){
        sortDirection = sortDirection.toUpperCase();
        if(!sortDirection.equals("ASC") && !sortDirection.equals("DESC"))
            throw new IntegrityError(null, "El parámetro sortDirection solo acepta el valor ASC o DESC.");
        size = (size > 10 || size < 1) ? 10 : size;
        Pageable paginacion = PageRequest.of(page, size);
        Page<Producto> productos = productoService.listar(paginacion, activo, sort, sortDirection);
        Page<ProductoRespuestaDTO> productosDTO = productos.map(ProductoRespuestaDTO::new);
        return ResponseEntity.ok().body(new ResponseWrapper<Page<ProductoRespuestaDTO>>(
            HttpStatus.OK, 
            "Listado de productos exitoso.", 
            null, 
            productosDTO));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ResponseWrapper<ProductoRespuestaDTO>> verProducto(
        @PathVariable Long id
    ){
        Producto producto = productoService.buscar(id);
        return ResponseEntity.ok().body(new ResponseWrapper<ProductoRespuestaDTO>(
            HttpStatus.OK, 
            "Detalles del producto " + producto.getNombre(), 
            null,
            new ProductoRespuestaDTO(producto)));
    }

    @PutMapping("/producto/{id}")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<ResponseWrapper<ProductoRespuestaDTO>> actualizarProducto(
        @PathVariable Long id,
        @RequestBody @Valid ProductoActualizarDTO datos
    ){
        Producto producto = productoService.actualizar(id, datos);
        return ResponseEntity.ok().body(new ResponseWrapper<ProductoRespuestaDTO>(
            HttpStatus.OK, 
            "Actualización de producto exitosa.", 
            null, 
            new ProductoRespuestaDTO(producto)));
    }

    @DeleteMapping("producto/{id}")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<Void> eliminarProducto(
        @PathVariable Long id,
        @RequestParam(defaultValue = "false") Boolean delete
    ){
        if(delete)
            productoService.eliminar(id);
        else    
            productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/productos/precio")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<Void> cambiarPrecio(
        @RequestParam(defaultValue = "true") Boolean round,
        @RequestParam(defaultValue = "10") Long scale,
        @RequestBody @Valid @Size(min = 1, message = "Al menos una versión es requerida.") List<ProductoVersionCambiarPrecioDTO> datos
    ){
        productoService.cambiarPrecio(datos, round, scale);
        return ResponseEntity.noContent().build();
    }
}
