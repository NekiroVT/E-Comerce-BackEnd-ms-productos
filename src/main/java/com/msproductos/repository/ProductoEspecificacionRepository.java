package com.msproductos.repository;

import com.msproductos.entities.ProductoEspecificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductoEspecificacionRepository extends JpaRepository<ProductoEspecificacion, UUID> {
}
