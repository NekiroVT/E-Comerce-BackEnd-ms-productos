package com.msproductos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductoCombinacionDTO {
    private UUID idCombinacion;
    private BigDecimal precio;
    private Integer stock;
    private String nombre;
    private List<String> imagenes;

    // Estos son los valores de las claves
    private String valorClave1;
    private String valorClave2;
    private UUID productoId;

}
