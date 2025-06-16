package com.msproductos.service.impl;

import com.msproductos.dto.ClaveDTO;
import com.msproductos.entities.ProductoClave;
import com.msproductos.repository.ProductoClaveRepository;
import com.msproductos.service.ClaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaveServiceImpl implements ClaveService {

    private final ProductoClaveRepository productoClaveRepository;

    @Override
    public Optional<UUID> crearClave(ClaveDTO dto) {
        String claveTexto = dto.getClave().trim(); // 👈 el nuevo nombre de campo
        boolean yaExiste = productoClaveRepository.existsByClaveIgnoreCase(claveTexto);
        if (yaExiste) {
            return Optional.empty(); // ❌ Ya existe
        }

        ProductoClave clave = new ProductoClave();
        clave.setId(UUID.randomUUID());
        clave.setClave(claveTexto);
        productoClaveRepository.save(clave);

        return Optional.of(clave.getId()); // ✅ Nueva clave creada
    }

}
