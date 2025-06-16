package com.msproductos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class DetalleProductoDTO {
    private UUID idProducto;
    private String nombre;
    private String descripcion;
    private String estado;
    private BigDecimal precio;
    private String urlImagenPrincipal; // ✅ AGREGADO AQUÍ
    private List<String> categorias;
    private List<ImagenDTO> imagenes;
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
        private String claveNombre;
        private String valorNombre;
    }
}
