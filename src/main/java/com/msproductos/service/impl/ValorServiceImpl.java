package com.msproductos.service.impl;

import com.msproductos.dto.ValorDTO;
import com.msproductos.entities.ValorClave;
import com.msproductos.repository.ValorClaveRepository;
import com.msproductos.service.ValorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValorServiceImpl implements ValorService {

    private final ValorClaveRepository valorClaveRepository;

    @Override
    public Optional<UUID> crearValor(ValorDTO dto) {
        String valorTexto = dto.getValor().trim();
        boolean yaExiste = valorClaveRepository.existsByValorIgnoreCase(valorTexto);
        if (yaExiste) {
            return Optional.empty();
        }

        ValorClave valor = new ValorClave();
        valor.setId(UUID.randomUUID());
        valor.setValor(valorTexto);
        valorClaveRepository.save(valor);

        return Optional.of(valor.getId());
    }
}
