package com.msproductos.repository;

import com.msproductos.entities.ValorClave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValorClaveRepository extends JpaRepository<ValorClave, UUID> {
    boolean existsByValorIgnoreCase(String valor);
}
