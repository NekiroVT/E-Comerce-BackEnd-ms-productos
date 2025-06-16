package com.msproductos.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-usuarios") // nombre registrado en Eureka
public interface UsuariosClient {


}
