package com.msproductos.service;

import com.msproductos.dto.ProductoCombinacionDTO;

import java.util.UUID;

public interface ProductoCombinacionService {
    ProductoCombinacionDTO obtenerPorId(UUID idCombinacion);
    ProductoCombinacionDTO obtenerStockPorCombinacion(UUID combinacionId);


}
