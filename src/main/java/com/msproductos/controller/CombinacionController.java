package com.msproductos.controller;

import com.msproductos.dto.ProductoCombinacionDTO;
import com.msproductos.service.ProductoCombinacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/combinaciones")
@RequiredArgsConstructor
public class CombinacionController {


    @Autowired
    private ProductoCombinacionService productoCombinacionService;

    private final ProductoCombinacionService combinacionService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductoCombinacionDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(combinacionService.obtenerPorId(id));
    }

    @GetMapping("/stock/{combinacionId}")
    public ResponseEntity<Integer> obtenerStock(@PathVariable UUID combinacionId) {
        ProductoCombinacionDTO combinacionDTO = productoCombinacionService.obtenerPorId(combinacionId);
        if (combinacionDTO != null) {
            return ResponseEntity.ok(combinacionDTO.getStock());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



}
