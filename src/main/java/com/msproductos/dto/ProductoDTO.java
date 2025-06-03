package com.msproductos.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.msproductos.util.FlexibleUUIDDeserializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductoDTO {

    private UUID id; // 👈 ID del producto

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stockTotal;

    private Boolean activo;
    private Double calificacionPromedio;
    private Integer numeroVentas;

    @JsonDeserialize(contentUsing = FlexibleUUIDDeserializer.class)
    private List<UUID> categoriaIds;

    private List<VariacionDTO> variaciones;
    private List<ImagenProductoDTO> imagenes;
    private List<ProductoEspecificacionDTO> especificaciones;
}
