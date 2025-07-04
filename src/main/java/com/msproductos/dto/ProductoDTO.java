package com.msproductos.dto;

import com.msproductos.enums.EstadoProducto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductoDTO {

    private UUID id;
    private String nombre;
    private EstadoProducto estado;

    private Integer stockTotal;
    private BigDecimal precioPrincipal;
    private String urlImagenPrincipal;
}
