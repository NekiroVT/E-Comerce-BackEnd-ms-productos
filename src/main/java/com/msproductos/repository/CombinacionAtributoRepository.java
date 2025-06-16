package com.msproductos.repository;

import com.msproductos.entities.CombinacionAtributo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CombinacionAtributoRepository extends JpaRepository<CombinacionAtributo, UUID> {
}
