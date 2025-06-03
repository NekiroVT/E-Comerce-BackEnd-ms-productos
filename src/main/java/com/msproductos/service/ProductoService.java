package com.msproductos.service;

import com.msproductos.dto.ProductoDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface ProductoService {
    ProductoDTO crearProducto(ProductoDTO dto, HttpHeaders headers);
    ProductoDTO obtenerProducto(UUID id);
    List<ProductoDTO> listarProductos();
    ProductoDTO actualizarProducto(UUID id, ProductoDTO dto);
    String eliminarProducto(UUID id);
}
