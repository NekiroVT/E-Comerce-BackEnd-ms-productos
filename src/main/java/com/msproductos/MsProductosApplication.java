package com.msproductos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 🔁 Para que se registre en Eureka
@EnableFeignClients     // 🤝 Para usar FeignClient si luego llamas a otros microservicios
public class MsProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductosApplication.class, args);
    }

}
