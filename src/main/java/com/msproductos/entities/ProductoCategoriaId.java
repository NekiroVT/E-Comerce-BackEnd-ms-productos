package com.msproductos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductoCategoriaId implements Serializable {

    @Column(name = "id_producto")
    private UUID productoId;

    @Column(name = "id_categoria")
    private UUID categoriaId;

    // Getters y setters

    public UUID getProductoId() {
        return productoId;
    }

    public void setProductoId(UUID productoId) {
        this.productoId = productoId;
    }

    public UUID getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(UUID categoriaId) {
        this.categoriaId = categoriaId;
    }

    // equals y hashCode

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
