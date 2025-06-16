package com.msproductos.service;

import java.util.UUID;

public interface ClaveValorRelacionService {
    boolean crearRelacion(UUID claveId, UUID valorId);
}
