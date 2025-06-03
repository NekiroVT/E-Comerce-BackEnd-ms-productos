package com.msproductos.repository;

import com.msproductos.entities.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    @EntityGraph(attributePaths = "categorias")
    @Query("SELECT p FROM Producto p WHERE p.idProducto = :id")
    Optional<Producto> findByIdWithCategorias(@Param("id") UUID id);

    @EntityGraph(attributePaths = "categorias")
    @Query("SELECT p FROM Producto p")
    List<Producto> findAllWithCategorias();
}
