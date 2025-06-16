package com.msproductos.dto;

import com.msproductos.enums.EstadoProducto;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaProductoDTO {
    private UUID id;
    private String nombre;
    private EstadoProducto estado;
    private BigDecimal precioPrincipal;
    private String urlImagenPrincipal;
    private Integer stockTotal;
}
