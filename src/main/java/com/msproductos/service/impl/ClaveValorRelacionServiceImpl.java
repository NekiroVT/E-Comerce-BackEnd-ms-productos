package com.msproductos.service.impl;

import com.msproductos.entities.*;
import com.msproductos.repository.*;
import com.msproductos.service.ClaveValorRelacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaveValorRelacionServiceImpl implements ClaveValorRelacionService {

    private final ProductoClaveRepository claveRepository;
    private final ValorClaveRepository valorRepository;
    private final ClaveValorRelacionRepository relacionRepository;

    @Override
    public boolean crearRelacion(UUID claveId, UUID valorId) {
        if (claveId == null || valorId == null) {
            throw new IllegalArgumentException("❌ claveId y valorId no pueden ser nulos");
        }

        // Verifica si la relación ya existe
        ClaveValorRelacionId id = new ClaveValorRelacionId(claveId, valorId);
        if (relacionRepository.existsById(id)) {
            return false;
        }

        // Busca la clave y el valor
        ProductoClave clave = claveRepository.findById(claveId)
                .orElseThrow(() -> new RuntimeException("❌ Clave no encontrada"));

        ValorClave valor = valorRepository.findById(valorId)
                .orElseThrow(() -> new RuntimeException("❌ Valor no encontrado"));

        // Crea la relación
        ClaveValorRelacion relacion = new ClaveValorRelacion(id, clave, valor);
        relacionRepository.save(relacion);

        return true;
    }
}
