package com.msproductos.service;

import com.msproductos.dto.*;
import com.msproductos.entities.ProductoUsuario;

import java.util.List;
import java.util.UUID;

public interface ProductoService {

    ProductoDTO crearProducto(ProductoRequest request, UUID usuarioId);

    void eliminarProducto(UUID productoId); // ✅ AGREGA ESTE MÉTODO
    List<TarjetaProductoDTO> obtenerTarjetasProductos();
    DetalleProductoDTO obtenerDetalleProducto(UUID id);
    // Cambia el método en el repositorio si es necesario

    public UsuarioCarritoDTO obtenerProductoUsuarioPorUsuarioId(UUID usuarioId);

}


