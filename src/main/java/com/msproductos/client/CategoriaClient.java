package com.msproductos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "ms-categorias") // Debe coincidir con spring.application.name del microservicio de categorías
public interface CategoriaClient {

    // ✅ Ruta correcta según tu @RequestMapping("/api/categorias")

    @GetMapping("/api/categorias/existe/{id}")
    boolean existeCategoria(@PathVariable("id") UUID id);

    @GetMapping("/api/categorias/nombre/{id}")
    String obtenerNombreCategoria(@PathVariable UUID id);
}

