package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto_categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCategoria {

    @EmbeddedId
    private ProductoCategoriaId id;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    // ❌ ¡NO repetir id_categoria aquí!
    // private UUID categoriaId;
}
