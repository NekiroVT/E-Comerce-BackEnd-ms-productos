package com.msproductos.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "producto_categoria")
public class ProductoCategoria {

    @EmbeddedId
    private ProductoCategoriaId id = new ProductoCategoriaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "id_categoria", nullable = false, insertable = false, updatable = false)
    private UUID idCategoria;

    // Getter y setter de ID compuesto
    public ProductoCategoriaId getId() {
        return id;
    }

    public void setId(ProductoCategoriaId id) {
        this.id = id;
    }

    // Relación al producto
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Getter / setter explícito del idCategoria
    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
        this.id.setCategoriaId(idCategoria); // sincroniza la clave compuesta
    }

    public void setProductoId(UUID productoId) {
        this.id.setProductoId(productoId); // sincroniza la clave compuesta
    }

    // ✅ Métodos auxiliares (recomendado para mapToDTO)
    public UUID getProductoId() {
        return id.getProductoId();
    }

    public UUID getCategoriaId() {
        return id.getCategoriaId();
    }
}
