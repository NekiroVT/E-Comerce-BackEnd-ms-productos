package com.msproductos.service;

import com.msproductos.dto.ValorDTO;

import java.util.Optional;
import java.util.UUID;

public interface ValorService {
    Optional<UUID> crearValor(ValorDTO dto);
}
