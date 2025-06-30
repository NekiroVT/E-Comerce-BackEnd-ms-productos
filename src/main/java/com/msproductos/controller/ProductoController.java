package com.msproductos.controller;

import com.msproductos.client.UsuariosClient;
import com.msproductos.dto.*;
import com.msproductos.entities.ProductoUsuario;
import com.msproductos.enums.EstadoProducto;
import com.msproductos.repository.ProductoUsuarioRepository;
import com.msproductos.service.ProductoService;
import com.msproductos.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msproductos.repository.ProductoRepository;
import com.msproductos.repository.ProductoCombinacionRepository;




import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;
    private final ProductoCombinacionRepository productoCombinacionRepository;
    private UsuariosClient usuariosClient;
    @Autowired
    private ProductoUsuarioRepository productoUsuarioRepository;


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

    @GetMapping("/existe/{id}")
    public ResponseEntity<?> existeProducto(
            @PathVariable String id,
            @RequestHeader("X-User-Permissions") String permisos) {

        if (permisos == null || !permisos.contains("productos:productos.read")) {
            return ResponseEntity.status(403).body(
                    Map.of("success", false, "mensaje", "❌ No tienes permiso para verificar productos")
            );
        }

        try {
            UUID uuid = UUIDUtil.parseFlexibleUUID(id);
            boolean existe = productoRepository.existsById(uuid);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "mensaje", "✅ Verificación completada",
                    "existe", existe
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ UUID inválido")
            );
        }
    }

    @GetMapping("/combinacion/existe/{id}")
    public ResponseEntity<?> existeCombinacion(
            @PathVariable String id,
            @RequestHeader("X-User-Permissions") String permisos) {

        if (permisos == null || !permisos.contains("productos:productos.read")) {
            return ResponseEntity.status(403).body(
                    Map.of("success", false, "mensaje", "❌ No tienes permiso para verificar combinaciones")
            );
        }

        try {
            UUID uuid = UUIDUtil.parseFlexibleUUID(id);
            boolean existe = productoCombinacionRepository.existsById(uuid);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "mensaje", "✅ Verificación completada",
                    "existe", existe
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "mensaje", "❌ UUID inválido")
            );
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public UsuarioCarritoDTO obtenerUsuarioParaCarrito(@PathVariable UUID usuarioId) {
        // Llamamos al servicio para obtener el DTO del usuario
        return productoService.obtenerProductoUsuarioPorUsuarioId(usuarioId);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable UUID id,
            @RequestBody CambiarEstadoProductoRequest request) {

        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(EstadoProducto.valueOf(request.getNuevoEstado()));
        productoRepository.save(producto);

        return ResponseEntity.ok().build();
    }






















}
