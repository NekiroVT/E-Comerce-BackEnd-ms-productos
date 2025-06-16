package com.msproductos.repository;

import com.msproductos.entities.Producto;
import com.msproductos.enums.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findByEstado(EstadoProducto estado);
    Optional<Producto> findById(UUID id);


}
