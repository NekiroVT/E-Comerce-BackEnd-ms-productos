package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "variacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variacion {

    @Id
    @Column(name = "id_variacion")
    private UUID idVariacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "nombre_variacion")
    private String nombreVariacion;

    private Integer stock;
}
