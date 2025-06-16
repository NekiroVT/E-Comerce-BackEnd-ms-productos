package com.msproductos.repository;

import com.msproductos.entities.ProductoCategoria;
import com.msproductos.entities.ProductoCategoriaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, ProductoCategoriaId> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductoCategoria pc WHERE pc.id.productoId = :productoId")
    void deleteByProductoId(@Param("productoId") UUID productoId);

    @Query("SELECT pc.id.categoriaId FROM ProductoCategoria pc WHERE pc.id.productoId = :productoId")
    List<UUID> findCategoriaIdsByProductoId(@Param("productoId") UUID productoId);
}
