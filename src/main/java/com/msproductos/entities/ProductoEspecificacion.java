package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "producto_especificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEspecificacion {

    @Id
    @Column(name = "id_especificacion")
    private UUID id;

    private String clave;
    private String valor;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
