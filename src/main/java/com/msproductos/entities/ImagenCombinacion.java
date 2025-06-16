package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "imagen_combinacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenCombinacion {

    @Id
    @Column(name = "id_imagen_combinacion")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "combinacion_id")
    private ProductoCombinacion combinacion;

    @Column(name = "url_imagen")
    private String urlImagen;

    private Integer orden;
}
