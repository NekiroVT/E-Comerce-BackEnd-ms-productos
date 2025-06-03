package com.msproductos.controller;

import com.msproductos.dto.ProductoDTO;
import com.msproductos.service.ProductoService;
import com.msproductos.util.UUIDUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🛡️ Crear producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO dto,
                                           @RequestHeader HttpHeaders headers) {
        String permisos = headers.getFirst("X-User-Permissions");
        if (permisos == null || !permisos.contains("productos:productos.create")) {
            return ResponseEntity.status(403).body("No tienes permiso para crear productos");
        }

        return ResponseEntity.ok(productoService.crearProducto(dto, headers));
    }

    // 🛡️ Obtener producto por ID
    // ✅ Obtener producto por ID sin verificación de permisos
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable String id) {
        try {
            UUID uuid = UUIDUtil.parseFlexibleUUID(id);
            return ResponseEntity.ok(productoService.obtenerProducto(uuid));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("❌ Producto no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("❌ " + e.getMessage());
        }
    }


    // 🛡️ Listar todos los productos
    //@GetMapping
    //public ResponseEntity<?> listarProductos(@RequestHeader("X-User-Permissions") String permisos) {
        // Comenta la validación de permisos si no quieres restringir
        // if (!permisos.contains("productos:productos.get")) {
        //     return ResponseEntity.status(403).body("No tienes permiso para listar productos");
        // }

        //return ResponseEntity.ok(productoService.listarProductos());
    //}

    @GetMapping
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos()); // No más verificación de permisos
    }



    // 🛡️ Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable String id,
            @RequestBody ProductoDTO dto,
            @RequestHeader("X-User-Permissions") String permisos
    ) {
        if (!permisos.contains("productos:productos.update")) {
            return ResponseEntity.status(403).body("No tienes permiso para editar productos");
        }

        try {
            UUID uuid = UUIDUtil.parseFlexibleUUID(id);
            return ResponseEntity.ok(productoService.actualizarProducto(uuid, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("❌ Producto no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("❌ " + e.getMessage());
        }
    }

    // 🛡️ Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable String id,
            @RequestHeader("X-User-Permissions") String permisos
    ) {
        if (!permisos.contains("productos:productos.delete")) {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar productos");
        }

        try {
            UUID uuid = UUIDUtil.parseFlexibleUUID(id);
            return ResponseEntity.ok(productoService.eliminarProducto(uuid));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("❌ Producto no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("❌ " + e.getMessage());
        }
    }
}
