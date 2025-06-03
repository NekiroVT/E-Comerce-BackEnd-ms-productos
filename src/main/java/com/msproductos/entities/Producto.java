package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @Column(name = "id_producto")
    private UUID idProducto;

    private String nombre;

    @Column(length = 4000)
    private String descripcion;

    private BigDecimal precio;

    @Column(name = "stock_total")
    private Integer stockTotal;

    private Boolean activo;

    @Column(name = "calificacion_promedio")
    private Double calificacionPromedio;

    @Column(name = "numero_ventas")
    private Integer numeroVentas;

    @Column(name = "vendedor_id")
    private UUID vendedorId;

    // ✅ Relación con producto_categoria
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoCategoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Variacion> variaciones = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEspecificacion> especificaciones = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenProducto> imagenes = new ArrayList<>();
}
