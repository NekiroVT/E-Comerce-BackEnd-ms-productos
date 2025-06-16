package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clave_valor_relacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaveValorRelacion {

    @EmbeddedId
    private ClaveValorRelacionId id;

    @ManyToOne
    @MapsId("claveId") // 🔁 usa el campo claveId del ID embebido
    @JoinColumn(name = "clave_id", nullable = false)
    private ProductoClave clave;

    @ManyToOne
    @MapsId("valorId") // 🔁 usa el campo valorId del ID embebido
    @JoinColumn(name = "valor_id", nullable = false)
    private ValorClave valor;
}
