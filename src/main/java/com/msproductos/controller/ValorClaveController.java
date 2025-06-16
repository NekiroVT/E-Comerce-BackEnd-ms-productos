package com.msproductos.controller;

import com.msproductos.dto.ValorDTO;
import com.msproductos.service.ValorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/valores")
@RequiredArgsConstructor
public class ValorClaveController {

    private final ValorService valorService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearValor(
            @RequestHeader("X-User-Permissions") String permisos,
            @RequestBody ValorDTO dto
    ) {
        if (!permisos.contains("productos:valor.create")) {
            return ResponseEntity.status(403).body(
                    Map.of(
                            "success", false,
                            "mensaje", "❌ No tienes permisos para esta operación"
                    )
            );
        }

        Optional<UUID> idValor = valorService.crearValor(dto);

        if (idValor.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "mensaje", "❌ Ya existe un valor con el nombre: " + dto.getValor()
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "mensaje", "✅ Valor creado correctamente"
                )
        );
    }
}
