package com.msproductos.client;

import com.msproductos.dto.RegistroLogisticaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "ms-logistica")
public interface LogisticaClient {

    @PostMapping("/api/producto-logistica/registrar")
    void registrarProductoLogistica(@RequestBody RegistroLogisticaRequest request);

}
