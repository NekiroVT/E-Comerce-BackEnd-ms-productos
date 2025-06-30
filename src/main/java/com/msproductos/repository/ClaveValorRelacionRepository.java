package com.msproductos.repository;

import com.msproductos.entities.ClaveValorRelacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import com.msproductos.entities.ClaveValorRelacionId;

public interface ClaveValorRelacionRepository extends JpaRepository<ClaveValorRelacion, ClaveValorRelacionId> {
    boolean existsByClave_IdAndValor_Id(UUID claveId, UUID valorId);


}
