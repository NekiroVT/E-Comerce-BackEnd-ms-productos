package com.msproductos.repository;

import com.msproductos.entities.ProductoUsuario;
import com.msproductos.entities.ProductoUsuario.ProductoUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoUsuarioRepository extends JpaRepository<ProductoUsuario, ProductoUsuarioId> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductoUsuario pu WHERE pu.productoId = :productoId")
    void deleteByProductoId(@Param("productoId") UUID productoId);
    @Query("SELECT p FROM ProductoUsuario p WHERE p.usuarioId = :usuarioId")
    List<ProductoUsuario> findByUsuarioId(UUID usuarioId);

}
