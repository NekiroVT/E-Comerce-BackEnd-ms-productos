package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "producto_clave")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoClave {

    @Id
    @Column(name = "id_clave", columnDefinition = "RAW(16)")
    private UUID id;


    @Column(nullable = false, unique = true)
    private String clave;
}
