package com.msproductos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroLogisticaRequest {
    private UUID productoId;
    private UUID usuarioId;
}
