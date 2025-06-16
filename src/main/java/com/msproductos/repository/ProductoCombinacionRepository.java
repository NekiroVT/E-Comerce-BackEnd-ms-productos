package com.msproductos.repository;

import com.msproductos.entities.ProductoCombinacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductoCombinacionRepository extends JpaRepository<ProductoCombinacion, UUID> {
}
