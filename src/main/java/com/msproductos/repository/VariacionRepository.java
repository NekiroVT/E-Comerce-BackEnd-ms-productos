package com.msproductos.repository;

import com.msproductos.entities.Variacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VariacionRepository extends JpaRepository<Variacion, UUID> {
}
