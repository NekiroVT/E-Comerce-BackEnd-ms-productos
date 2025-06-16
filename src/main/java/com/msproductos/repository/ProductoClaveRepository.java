package com.msproductos.repository;

import com.msproductos.entities.ProductoClave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductoClaveRepository extends JpaRepository<ProductoClave, UUID> {

    boolean existsByClaveIgnoreCase(String nombre);


}
