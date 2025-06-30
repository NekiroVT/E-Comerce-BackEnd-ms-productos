package com.msproductos.repository;

import com.msproductos.entities.CombinacionAtributo;
import com.msproductos.entities.ProductoCombinacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CombinacionAtributoRepository extends JpaRepository<CombinacionAtributo, UUID> {
    public abstract List<CombinacionAtributo> findByCombinacion(ProductoCombinacion combinacion);



}
