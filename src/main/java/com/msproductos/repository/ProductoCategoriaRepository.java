package com.msproductos.repository;

import com.msproductos.entities.ProductoCategoria;
import com.msproductos.entities.ProductoCategoriaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, ProductoCategoriaId> {
}
