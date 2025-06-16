package com.msproductos.service;

import com.msproductos.dto.ClaveDTO;

import java.util.Optional;
import java.util.UUID;

public interface ClaveService {
    Optional<UUID> crearClave(ClaveDTO dto);
}
