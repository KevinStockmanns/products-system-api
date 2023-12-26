package com.products.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.products.dto.categoria.CategoriaActualizarDTO;
import com.products.dto.categoria.CategoriaRegistroDTO;
import com.products.dto.producto.ProductoActualizarDTO;
import com.products.dto.producto.ProductoRegistroDTO;
import com.products.dto.productoVersion.ProductoVersionActualizarDTO;
import com.products.dto.productoVersion.ProductoVersionCambiarPrecioDTO;
import com.products.dto.productoVersion.ProductoVersionRegistroDTO;
import com.products.entity.Categoria;
import com.products.entity.HistorialPrecio;
import com.products.entity.Producto;
import com.products.entity.ProductoVersion;
import com.products.error.IntegrityError;
import com.products.repository.CategoriaRepository;
import com.products.repository.HistorialPrecioRepository;
import com.products.repository.ProductoRepository;
import com.products.repository.ProductoVersionRepository;
import com.products.validations.producto.actualizar.ValidarActualizarProducto;
import com.products.validations.producto.registrar.ValidarRegistroProducto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVersionRepository productoVersionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private HistorialPrecioRepository historialPrecioRepository;

    @Autowired
    private List<ValidarRegistroProducto> validacionesRegistro;

    @Autowired
    private List<ValidarActualizarProducto> validacionesActualizar;

    public Producto crear(@Valid ProductoRegistroDTO datos) {
        validacionesRegistro.forEach(v-> v.validar(datos));

        Producto producto = new Producto(datos);

        List<ProductoVersion> productoVersions = new ArrayList<>();
        for(ProductoVersionRegistroDTO pv: datos.versiones()){
            ProductoVersion productoVersion = new ProductoVersion(pv);
            productoVersion.setProducto(producto);
            productoVersions.add(productoVersion);
        }

        List<Categoria> categorias = new ArrayList<>();
        if(datos.categorias() != null){
            for(CategoriaRegistroDTO c : datos.categorias()){
                Categoria categoria = categoriaRepository.findByNombre(c.nombre());
                if(categoria == null){
                    categoria = new Categoria(c);
                    categoria = categoriaRepository.save(categoria);
                }
                categorias.add(categoria);
            }
        }

        producto.setCategorias(categorias);
        producto.setVersiones(productoVersions);
        producto = productoRepository.save(producto);
        productoVersions=productoVersionRepository.saveAll(productoVersions);
        return producto;
    }

    public Page<Producto> listar(Pageable paginacion, Boolean activo, String sort, String sortDirection) {
        Page<Producto> productos = null;
        if(sort.equalsIgnoreCase("vistas"))
            if(sortDirection.equalsIgnoreCase("ASC"))
                productos = productoRepository.findAllByEstadoAndOrderedVistasAsc(paginacion, activo);
            else
                productos = productoRepository.findAllByEstadoAndOrderedVistasDesc(paginacion, activo);
        else if(sort.equalsIgnoreCase("ventas"))
            if(sortDirection.equalsIgnoreCase("ASC"))
                productos = productoRepository.findAllByEstadoAndOrderedVentasAsc(paginacion, activo);
            else
                productos = productoRepository.findAllByEstadoAndOrderedVentasDesc(paginacion, activo);
        else if(sort.equalsIgnoreCase("pedidos"))
            if(sortDirection.equalsIgnoreCase("ASC"))
                productos = productoRepository.findAllByEstadoAndOrderedPedidosAsc(paginacion, activo);
            else
                productos = productoRepository.findAllByEstadoAndOrderedPedidosDesc(paginacion, activo);
        else
            if(sortDirection.equalsIgnoreCase("ASC"))
                productos = productoRepository.findAllByEstadoAndOrderedNombreAsc(paginacion, activo);
            else
                productos = productoRepository.findAllByEstadoAndOrderedNombreDesc(paginacion, activo);
        return productos;
    }

    @Transactional
    public Producto buscar(Long id) {
        Producto producto = productoRepository.getReferenceById(id);
        producto.aumentarVisita();
        return producto;
    }

    @Transactional
    public Producto actualizar(Long id, @Valid ProductoActualizarDTO datos) {
        Producto proudcto = productoRepository.getReferenceById(id);
        if(proudcto == null)
            throw new IntegrityError(null, "No se encontro el elemento en la base de datos para el id " + id);
        validacionesActualizar.forEach(v-> v.validar(datos, proudcto));

        proudcto.actualizar(datos);
        
        List<ProductoVersion> productoVersions = new ArrayList<>();
        if(datos.versiones() != null){
            for(ProductoVersionActualizarDTO verDTO : datos.versiones()){
                ProductoVersion pVersion = productoVersionRepository.getReferenceById(verDTO.id());
                if(verDTO.precio() != null)
                    if(!verDTO.precio().equals(pVersion.getPrecio()))
                        registrarCambioPrecio(pVersion);
                pVersion.actualizar(verDTO);
                productoVersions.add(pVersion);
            }
        }
        if(productoVersions.size() > 0){
            proudcto.setVersiones(productoVersions);
            proudcto.revisarEstado();
        }

        List<Categoria> categorias = new ArrayList<>();
        if(datos.categorias() != null){
            for(CategoriaActualizarDTO catDTO : datos.categorias()){
                Categoria categoria = categoriaRepository.findByNombre(catDTO.nombre());
                if(categoria == null)
                    categoria = new Categoria(catDTO.nombre());
                categorias.add(categoria);
            }
        }
        if(categorias.size()>0)
            proudcto.setCategorias(categorias);
        
        return proudcto;
    }

    private void registrarCambioPrecio(ProductoVersion pVersionSinAumento) {
        HistorialPrecio historialPrecio = new HistorialPrecio(pVersionSinAumento);
        historialPrecioRepository.save(historialPrecio);
    }

    public void eliminar(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isEmpty())
            throw new EntityNotFoundException("IntegrityError: El producto con el id '" + id + "' no se encuentra en la base de datos.");
        
        productoVersionRepository.deleteAll(producto.get().getVersiones());
        productoRepository.delete(producto.get());
    }

    @Transactional
    public void desactivar(Long id) {
        Producto producto = productoRepository.getReferenceById(id);

        producto.desactivar();
    }


    @Transactional
    public void cambiarPrecio(
            @Valid @Size(min = 1, message = "Al menos una versión es requerida.") List<ProductoVersionCambiarPrecioDTO> datos,
            Boolean round,
            Long scale) {
        for(ProductoVersionCambiarPrecioDTO dato: datos){
            ProductoVersion pVersion = productoVersionRepository.getReferenceById(dato.id());
            this.registrarCambioPrecio(pVersion);
            if(round){
                pVersion.setPrecio(this.redondear(dato.precio(), scale));
                pVersion.setPrecioReventa(this.redondear(dato.precioReventa(), scale));
            }else{
                pVersion.setPrecio(dato.precio());
                pVersion.setPrecioReventa(dato.precioReventa());
            }
        }
    }

    private BigDecimal redondear(BigDecimal valorInicial, long scale){

        scale = (scale != 10 && scale != 100) ? 10 : scale;
        BigDecimal resto = valorInicial.remainder(BigDecimal.valueOf(scale));
        BigDecimal redondeo = BigDecimal.ZERO;

        boolean condicion = resto.compareTo(BigDecimal.valueOf(3L)) >= 0;
        if(scale > 10L){
            condicion = resto.compareTo(BigDecimal.valueOf(60L)) > 0;
        }

        if(condicion){
            redondeo = valorInicial.add(BigDecimal.valueOf(scale).subtract(resto));
        }else{
            redondeo = valorInicial.subtract(resto);
        }
        return redondeo;
    }


}
