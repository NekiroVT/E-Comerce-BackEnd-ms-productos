package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "valor_clave")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorClave {

    @Id
    @Column(name = "id_valor", columnDefinition = "RAW(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String valor;
}



