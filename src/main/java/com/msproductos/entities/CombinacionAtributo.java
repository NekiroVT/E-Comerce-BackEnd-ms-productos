package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "combinacion_atributo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinacionAtributo {

    @Id
    @Column(name = "id_atributo")
    private UUID idAtributo;

    // Relación con la combinación a la que pertenece
    @ManyToOne
    @JoinColumn(name = "combinacion_id", nullable = false)
    private ProductoCombinacion combinacion;

    // Relación con la clave predefinida
    @ManyToOne
    @JoinColumn(name = "clave_id", nullable = false)
    private ProductoClave clave;

    // 🔁 Relación con el valor válido para esa clave
    @ManyToOne
    @JoinColumn(name = "valor_id", nullable = false)
    private ValorClave valor;
}
