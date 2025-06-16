package com.msproductos.dto;

import com.msproductos.enums.EstadoProducto;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.msproductos.util.FlexibleUUIDDeserializer;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductoRequest {

    private String nombre;
    private String descripcion;

    private EstadoProducto estado; // ✅ Enum: ACTIVO o INACTIVO

    @JsonDeserialize(contentUsing = FlexibleUUIDDeserializer.class)
    private List<UUID> categorias;

    private List<ImagenDTO> imagenes; // Imágenes generales del producto

    private List<EspecificacionDTO> especificaciones;

    private List<CombinacionDTO> combinaciones;

    @Data
    public static class ImagenDTO {
        private String urlImagen;
        private Integer orden;
    }

    @Data
    public static class EspecificacionDTO {
        private String clave;
        private String valor;
    }

    @Data
    public static class CombinacionDTO {
        private Integer stock;
        private BigDecimal precio;
        private boolean esPrincipal;

        private List<AtributoDTO> atributos;
        private List<ImagenDTO> imagenes;
    }

    @Data
    public static class AtributoDTO {
        @JsonDeserialize(using = FlexibleUUIDDeserializer.class)
        private UUID claveId;

        @JsonDeserialize(using = FlexibleUUIDDeserializer.class)
        private UUID valorId;
    }
}
