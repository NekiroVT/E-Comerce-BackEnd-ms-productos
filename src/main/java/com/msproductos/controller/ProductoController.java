package com.msproductos.controller;

import com.msproductos.dto.ProductoDTO;
import com.msproductos.dto.ProductoRequest;
import com.msproductos.dto.TarjetaProductoDTO;
import com.msproductos.service.ProductoService;
import com.msproductos.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msproductos.dto.DetalleProductoDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crearProducto(
            @RequestBody ProductoRequest request,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Permissions") String permisos) {

        // 🔐 Validar permisos
        if (permisos == null || !permisos.contains("productos:productos.create")) {
            return ResponseEntity.status(403).body(
                    Map.of("success", false, "mensaje", "❌ No tienes permiso para crear productos")
            );
        }

        // 🧠 Convertir UUID flexible
        UUID usuarioId;
        try {
            usuarioId = UUIDUtil.parseFlexibleUUID(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ UUID de usuario inválido")
            );
        }

        // 🚀 Crear producto
        ProductoDTO producto = productoService.crearProducto(request, usuarioId);
        return ResponseEntity.ok(
                Map.of("success", true, "producto", producto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable String id,
            @RequestHeader("X-User-Permissions") String permisos) {

        // 🔐 Validar permisos
        if (permisos == null || !permisos.contains("productos:productos.delete")) {
            return ResponseEntity.status(403).body(
                    Map.of("success", false, "mensaje", "❌ No tienes permiso para eliminar productos")
            );
        }

        // 🧠 Parsear UUID manualmente (acepta sin guiones y mayúsculas)
        UUID productoId;
        try {
            productoId = UUIDUtil.parseFlexibleUUID(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ UUID inválido")
            );
        }

        // 🗑️ Eliminar producto
        try {
            productoService.eliminarProducto(productoId);
            return ResponseEntity.ok(
                    Map.of("success", true, "mensaje", "✅ Producto eliminado correctamente")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", e.getMessage())
            );
        }
    }

    @GetMapping("/tarjetas")
    public ResponseEntity<?> obtenerProductosParaCards() {
        List<TarjetaProductoDTO> tarjetas = productoService.obtenerTarjetasProductos();
        return ResponseEntity.ok(Map.of("success", true, "productos", tarjetas));
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<DetalleProductoDTO> obtenerDetalleProducto(@PathVariable String id) {
        try {
            // 🔁 Convierte UUID flexible (sin guiones, en mayúscula, etc.)
            var uuid = UUIDUtil.parseFlexibleUUID(id);
            var dto = productoService.obtenerDetalleProducto(uuid);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // UUID malformado
        }
    }



}
