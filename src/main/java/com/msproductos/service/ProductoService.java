package com.msproductos.service;

import com.msproductos.dto.DetalleProductoDTO;
import com.msproductos.dto.ProductoDTO;
import com.msproductos.dto.ProductoRequest;
import com.msproductos.dto.TarjetaProductoDTO;

import java.util.List;
import java.util.UUID;

public interface ProductoService {

    ProductoDTO crearProducto(ProductoRequest request, UUID usuarioId);

    void eliminarProducto(UUID productoId); // ✅ AGREGA ESTE MÉTODO
    List<TarjetaProductoDTO> obtenerTarjetasProductos();
    DetalleProductoDTO obtenerDetalleProducto(UUID id);


}
