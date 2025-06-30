package com.msproductos.client;


import com.msproductos.dto.UsuarioSimpleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-usuarios") // nombre registrado en Eureka
public interface UsuariosClient {
    @GetMapping("/api/usuarios/{id}")
    UsuarioSimpleDTO obtenerUsuarioPorId(@PathVariable("id") UUID id);


}
