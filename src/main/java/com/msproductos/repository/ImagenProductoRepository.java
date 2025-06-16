package com.msproductos.repository;

import com.msproductos.entities.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, UUID> {
}
