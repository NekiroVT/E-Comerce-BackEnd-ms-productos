package com.msproductos.service.impl;

import com.msproductos.client.CategoriaClient;
import com.msproductos.dto.*;
import com.msproductos.entities.*;
import com.msproductos.repository.*;
import com.msproductos.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaClient categoriaClient;
    private final ProductoCategoriaRepository productoCategoriaRepository;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaClient categoriaClient,
            ProductoCategoriaRepository productoCategoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaClient = categoriaClient;
        this.productoCategoriaRepository = productoCategoriaRepository;
    }

    @Override
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto, HttpHeaders headers) {
        if (!categoriaClient.existenCategorias(dto.getCategoriaIds())) {
            throw new RuntimeException("❌ Una o más categorías no existen.");
        }

        Producto producto = new Producto();
        producto.setIdProducto(UUID.randomUUID());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStockTotal(dto.getStockTotal());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        producto.setCalificacionPromedio(dto.getCalificacionPromedio() != null ? dto.getCalificacionPromedio() : 0.0);
        producto.setNumeroVentas(dto.getNumeroVentas() != null ? dto.getNumeroVentas() : 0);

        String userId = headers.getFirst("X-User-Id");
        if (userId == null) {
            throw new RuntimeException("Falta el header X-User-Id");
        }
        producto.setVendedorId(UUID.fromString(userId));

        List<Variacion> variaciones = dto.getVariaciones().stream().map(v -> {
            Variacion var = new Variacion();
            var.setIdVariacion(UUID.randomUUID());
            var.setProducto(producto);
            var.setNombreVariacion(v.getNombreVariacion());
            var.setStock(v.getStock());
            return var;
        }).collect(Collectors.toList());
        producto.setVariaciones(variaciones);

        List<ProductoEspecificacion> especificaciones = dto.getEspecificaciones().stream().map(e -> {
            ProductoEspecificacion pe = new ProductoEspecificacion();
            pe.setIdEspecificacion(UUID.randomUUID());
            pe.setProducto(producto);
            pe.setClave(e.getClave());
            pe.setValor(e.getValor());
            return pe;
        }).collect(Collectors.toList());
        producto.setEspecificaciones(especificaciones);

        List<ImagenProducto> imagenes = dto.getImagenes().stream().map(img -> {
            ImagenProducto ip = new ImagenProducto();
            ip.setIdImagen(UUID.randomUUID());
            ip.setProducto(producto);
            ip.setUrlImagen(img.getUrlImagen());
            ip.setOrden(img.getOrden());
            return ip;
        }).collect(Collectors.toList());
        producto.setImagenes(imagenes);

        productoRepository.save(producto);

        // 🔁 Guardar relaciones producto ↔ categoría
        List<ProductoCategoria> relaciones = dto.getCategoriaIds().stream().map(categoriaId -> {
            ProductoCategoria relacion = new ProductoCategoria();
            relacion.setProducto(producto);
            relacion.setProductoId(producto.getIdProducto());
            relacion.setIdCategoria(categoriaId);
            return relacion;
        }).collect(Collectors.toList());
        productoCategoriaRepository.saveAll(relaciones);

        // 🔁 Devolver el DTO con las categorías directamente asignadas
        ProductoDTO respuesta = mapToDTO(producto);
        respuesta.setCategoriaIds(dto.getCategoriaIds());
        return respuesta;
    }

    @Override
    public ProductoDTO obtenerProducto(UUID id) {
        Producto producto = productoRepository.findByIdWithCategorias(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAllWithCategorias().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO actualizarProducto(UUID id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStockTotal(dto.getStockTotal());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        producto.setCalificacionPromedio(dto.getCalificacionPromedio() != null ? dto.getCalificacionPromedio() : 0.0);
        producto.setNumeroVentas(dto.getNumeroVentas() != null ? dto.getNumeroVentas() : 0);
        productoRepository.save(producto);
        return dto;
    }

    @Override
    public String eliminarProducto(UUID id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
        return "Producto eliminado correctamente";
    }

    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();

        dto.setId(producto.getIdProducto());

        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStockTotal(producto.getStockTotal());
        dto.setActivo(producto.getActivo());
        dto.setCalificacionPromedio(producto.getCalificacionPromedio());
        dto.setNumeroVentas(producto.getNumeroVentas());

        dto.setVariaciones(producto.getVariaciones().stream().map(v -> {
            VariacionDTO vdto = new VariacionDTO();
            vdto.setNombreVariacion(v.getNombreVariacion());
            vdto.setStock(v.getStock());
            return vdto;
        }).collect(Collectors.toList()));

        dto.setEspecificaciones(producto.getEspecificaciones().stream().map(e -> {
            ProductoEspecificacionDTO edto = new ProductoEspecificacionDTO();
            edto.setClave(e.getClave());
            edto.setValor(e.getValor());
            return edto;
        }).collect(Collectors.toList()));

        dto.setImagenes(producto.getImagenes().stream().map(i -> {
            ImagenProductoDTO idto = new ImagenProductoDTO();
            idto.setUrlImagen(i.getUrlImagen());
            idto.setOrden(i.getOrden());
            return idto;
        }).collect(Collectors.toList()));

        dto.setCategoriaIds(producto.getCategorias().stream()
                .map(rel -> rel.getId().getCategoriaId())
                .collect(Collectors.toList()));

        return dto;
    }
}
