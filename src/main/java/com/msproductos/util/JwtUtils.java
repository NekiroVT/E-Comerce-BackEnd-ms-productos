package com.msproductos.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class JwtUtils {

    public static UUID getUserIdFromToken(String token) {
        try {
            // Separa las partes del JWT
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Token JWT inválido");
            }

            // Decodifica el payload (2da parte del JWT)
            String payloadJson = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            // Extrae el campo "sub" del JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode payload = mapper.readTree(payloadJson);
            String sub = payload.get("sub").asText();

            return UUID.fromString(sub);
        } catch (Exception e) {
            throw new RuntimeException("❌ No se pudo extraer el UUID del token JWT", e);
        }
    }
}
