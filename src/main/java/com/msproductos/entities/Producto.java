package com.msproductos.entities;

import com.msproductos.enums.EstadoProducto;
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
    private UUID id;

    private String nombre;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;

    @Column(name = "clave_controla_imagenes")
    private String claveControlaImagenes; // ✅ Clave UUID como texto

    @Transient
    private Integer stockTotal;

    @Transient
    private BigDecimal precioPrincipal;

    @Transient
    private String urlImagenPrincipal;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenProducto> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEspecificacion> especificaciones = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoCombinacion> combinaciones = new ArrayList<>();
}
