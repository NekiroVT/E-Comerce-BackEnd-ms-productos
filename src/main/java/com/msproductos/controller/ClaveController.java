package com.msproductos.controller;

import com.msproductos.dto.ClaveDTO;
import com.msproductos.service.ClaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/claves")
@RequiredArgsConstructor
public class ClaveController {

    private final ClaveService claveService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearClave(
            @RequestHeader("X-User-Permissions") String permisos,
            @RequestBody ClaveDTO dto
    ) {
        if (!permisos.contains("productos:clave.create")) {
            return ResponseEntity.status(403).body(
                    Map.of(
                            "success", false,
                            "mensaje", "❌ No tienes permisos para esta operación"
                    )
            );
        }

        Optional<UUID> idClave = claveService.crearClave(dto);

        if (idClave.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "mensaje", "❌ Ya existe una clave con el nombre: " + dto.getClave()
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "mensaje", "✅ Clave creada correctamente"
                )
        );
    }




}
