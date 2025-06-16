package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCategoriaId implements Serializable {

    @Column(name = "id_producto")
    private UUID productoId;

    @Column(name = "id_categoria")
    private UUID categoriaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoCategoriaId that)) return false;
        return Objects.equals(productoId, that.productoId) &&
                Objects.equals(categoriaId, that.categoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, categoriaId);
    }
}
