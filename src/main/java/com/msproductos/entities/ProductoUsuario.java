package com.msproductos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "producto_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductoUsuario.ProductoUsuarioId.class)
public class ProductoUsuario {

    @Id
    @Column(name = "producto_id", columnDefinition = "RAW(16)")
    private UUID productoId;

    @Id
    @Column(name = "usuario_id", columnDefinition = "RAW(16)")
    private UUID usuarioId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // 👉 Constructor personalizado
    public ProductoUsuario(UUID productoId, UUID usuarioId) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.fechaCreacion = LocalDateTime.now();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoUsuarioId implements Serializable {
        private UUID productoId;
        private UUID usuarioId;
    }
}
