package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "producto_combinacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCombinacion {

    @Id
    @Column(name = "id_combinacion")
    private UUID idCombinacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer stock;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "es_principal")
    private boolean esPrincipal;

    @OneToMany(mappedBy = "combinacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CombinacionAtributo> atributos = new ArrayList<>();

    @OneToMany(mappedBy = "combinacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenCombinacion> imagenes = new ArrayList<>();
}
