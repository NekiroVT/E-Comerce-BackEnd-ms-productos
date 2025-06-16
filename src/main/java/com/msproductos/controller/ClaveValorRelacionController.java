package com.msproductos.controller;

import com.msproductos.dto.ClaveValorRelacionDTO;
import com.msproductos.service.ClaveValorRelacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clavevalorrelacion")
@RequiredArgsConstructor
public class ClaveValorRelacionController {

    private final ClaveValorRelacionService claveValorRelacionService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> relacionarClaveValor(
            @RequestHeader("X-User-Permissions") String permisos,
            @RequestBody ClaveValorRelacionDTO dto
    ) {
        if (!permisos.contains("productos:clavevalor.create")) {
            return ResponseEntity.status(403).body(
                    Map.of(
                            "success", false,
                            "mensaje", "❌ No tienes permisos para esta operación"
                    )
            );
        }

        UUID claveId = dto.getClaveId();
        UUID valorId = dto.getValorId();

        if (claveId == null || valorId == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ claveId y valorId son obligatorios")
            );
        }

        boolean creada = claveValorRelacionService.crearRelacion(claveId, valorId);

        if (!creada) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ Ya existe una relación con esos valores")
            );
        }

        return ResponseEntity.ok(
                Map.of("success", true, "mensaje", "✅ Relación clave-valor creada correctamente")
        );
    }
}
