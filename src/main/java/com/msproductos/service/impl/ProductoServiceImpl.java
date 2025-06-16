package com.msproductos.service.impl;

import com.msproductos.client.CategoriaClient;
import com.msproductos.dto.ProductoDTO;
import com.msproductos.dto.ProductoRequest;
import com.msproductos.dto.TarjetaProductoDTO;
import com.msproductos.entities.*;
import com.msproductos.enums.EstadoProducto;
import com.msproductos.repository.*;
import com.msproductos.service.ProductoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.msproductos.repository.ValorClaveRepository;
import com.msproductos.dto.DetalleProductoDTO;

import com.msproductos.entities.*;
import com.msproductos.repository.*;



import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoUsuarioRepository productoUsuarioRepository;
    private final ProductoClaveRepository productoClaveRepository;
    private final CategoriaClient categoriaClient;
    private final ValorClaveRepository valorClaveRepository;
    private final ClaveValorRelacionRepository claveValorRelacionRepository;
    private final ProductoCategoriaRepository productoCategoriaRepository;







    @Override
    @Transactional
    public ProductoDTO crearProducto(ProductoRequest request, UUID usuarioId) {
        // 1. Validar combinación principal
        if (request.getCombinaciones().stream().noneMatch(ProductoRequest.CombinacionDTO::isEsPrincipal)) {
            throw new RuntimeException("❌ Debe haber al menos una combinación principal");
        }

        // 2. Validar categorías
        for (UUID categoriaId : request.getCategorias()) {
            boolean existe = categoriaClient.existeCategoria(categoriaId);
            if (!existe) {
                throw new RuntimeException("❌ Categoría no válida: " + categoriaId);
            }
        }

        // 3. Crear producto
        Producto producto = new Producto();
        producto.setId(UUID.randomUUID());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setEstado(request.getEstado());

        // 4. Imágenes generales
        List<ImagenProducto> imagenes = request.getImagenes().stream()
                .map(img -> new ImagenProducto(UUID.randomUUID(), producto, img.getUrlImagen(), img.getOrden()))
                .collect(Collectors.toList());
        producto.setImagenes(imagenes);

        // 5. Especificaciones
        List<ProductoEspecificacion> especificaciones = request.getEspecificaciones().stream()
                .map(es -> new ProductoEspecificacion(UUID.randomUUID(), es.getClave(), es.getValor(), producto))
                .collect(Collectors.toList());
        producto.setEspecificaciones(especificaciones);

        // 6. Combinaciones
        List<ProductoCombinacion> combinaciones = new ArrayList<>();
        for (ProductoRequest.CombinacionDTO combDTO : request.getCombinaciones()) {
            ProductoCombinacion combinacion = new ProductoCombinacion();
            combinacion.setIdCombinacion(UUID.randomUUID());
            combinacion.setProducto(producto);
            combinacion.setStock(combDTO.getStock());
            combinacion.setPrecio(combDTO.getPrecio());
            combinacion.setEsPrincipal(combDTO.isEsPrincipal());

            // 6.1 Atributos
            List<CombinacionAtributo> atributos = new ArrayList<>();
            Set<UUID> clavesUsadas = new HashSet<>();

            for (ProductoRequest.AtributoDTO atr : combDTO.getAtributos()) {

                if (!clavesUsadas.add(atr.getClaveId())) {
                    throw new RuntimeException("❌ No se puede repetir la clave dentro de una misma combinación: " + atr.getClaveId());
                }

                ProductoClave clave = productoClaveRepository.findById(atr.getClaveId())
                        .orElseThrow(() -> new RuntimeException("❌ Clave no válida: " + atr.getClaveId()));

                ValorClave valor = valorClaveRepository.findById(atr.getValorId())
                        .orElseThrow(() -> new RuntimeException("❌ Valor no encontrado: " + atr.getValorId()));

                boolean relacionado = claveValorRelacionRepository.existsByClave_IdAndValor_Id(clave.getId(), valor.getId());

                if (!relacionado) {
                    throw new RuntimeException("❌ El valor '" + valor.getValor() + "' no está permitido para la clave: " + clave.getClave());
                }

                CombinacionAtributo atributo = new CombinacionAtributo();
                atributo.setIdAtributo(UUID.randomUUID());
                atributo.setCombinacion(combinacion);
                atributo.setClave(clave);
                atributo.setValor(valor);
                atributos.add(atributo);
            }

            combinacion.setAtributos(atributos);



            // 6.2 Imágenes
            List<ImagenCombinacion> imagenesCombinacion = new ArrayList<>();
            for (ProductoRequest.ImagenDTO img : combDTO.getImagenes()) {
                boolean existe = imagenes.stream().anyMatch(g -> g.getUrlImagen().equals(img.getUrlImagen()));
                if (!existe) {
                    throw new RuntimeException("❌ Imagen de combinación no existe en galería general: " + img.getUrlImagen());
                }
                ImagenCombinacion imgComb = new ImagenCombinacion(
                        UUID.randomUUID(), combinacion, img.getUrlImagen(), img.getOrden()
                );
                imagenesCombinacion.add(imgComb);
            }
            combinacion.setImagenes(imagenesCombinacion);

            combinaciones.add(combinacion);
        }
        producto.setCombinaciones(combinaciones);

        // 7. Guardar producto
        productoRepository.save(producto);

        // 7.1 Relacionar con categorías
        for (UUID categoriaId : request.getCategorias()) {
            ProductoCategoria pc = new ProductoCategoria();
            pc.setId(new ProductoCategoriaId(producto.getId(), categoriaId));
            pc.setProducto(producto);
            productoCategoriaRepository.save(pc);
        }

        // 8. Relacionar con usuario
        productoUsuarioRepository.save(new ProductoUsuario(producto.getId(), usuarioId));

        // 9. Calcular resumen para DTO
        ProductoCombinacion principal = producto.getCombinaciones().stream()
                .filter(ProductoCombinacion::isEsPrincipal)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("❌ No hay combinación principal"));

        int stockTotal = producto.getCombinaciones().stream()
                .mapToInt(ProductoCombinacion::getStock)
                .sum();

        String urlImagenPrincipal = principal.getImagenes().stream()
                .sorted(Comparator.comparing(ImagenCombinacion::getOrden))
                .map(ImagenCombinacion::getUrlImagen)
                .findFirst()
                .orElse(null);

        // 10. Retornar DTO
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .estado(producto.getEstado())
                .stockTotal(stockTotal)
                .precioPrincipal(principal.getPrecio())
                .urlImagenPrincipal(urlImagenPrincipal)
                .build();
    }

    @Override
    @Transactional
    public void eliminarProducto(UUID productoId) {
        // 1. Verificar si existe el producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("❌ Producto no encontrado con ID: " + productoId));

        // 2. Eliminar relaciones intermedias (manual porque no están en cascade)
        productoCategoriaRepository.deleteByProductoId(productoId);
        productoUsuarioRepository.deleteByProductoId(productoId);

        // 3. Eliminar el producto (esto elimina combinaciones, imágenes, especificaciones por cascade)
        productoRepository.delete(producto);
    }

    @Override
    @Transactional
    public List<TarjetaProductoDTO> obtenerTarjetasProductos() {
        List<Producto> productos = productoRepository.findByEstado(EstadoProducto.ACTIVO);


        return productos.stream()
                .map(producto -> {
                    ProductoCombinacion principal = producto.getCombinaciones().stream()
                            .filter(ProductoCombinacion::isEsPrincipal)
                            .findFirst()
                            .orElse(null);

                    if (principal == null) return null;

                    String urlImagenPrincipal = principal.getImagenes().stream()
                            .sorted(Comparator.comparing(ImagenCombinacion::getOrden))
                            .map(ImagenCombinacion::getUrlImagen)
                            .findFirst()
                            .orElse(null);

                    int stockTotal = producto.getCombinaciones().stream()
                            .mapToInt(ProductoCombinacion::getStock)
                            .sum();

                    return TarjetaProductoDTO.builder()
                            .id(producto.getId())
                            .nombre(producto.getNombre())
                            .estado(producto.getEstado())
                            .precioPrincipal(principal.getPrecio())
                            .urlImagenPrincipal(urlImagenPrincipal)
                            .stockTotal(stockTotal)
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public DetalleProductoDTO obtenerDetalleProducto(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Producto no encontrado con ID: " + id));

        DetalleProductoDTO dto = new DetalleProductoDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setEstado(producto.getEstado().name());

        // ✅ Obtener IDs de categorías usando el repo intermedio (sin tocar la entidad)
        List<UUID> categoriaIds = productoCategoriaRepository.findCategoriaIdsByProductoId(producto.getId());
        List<String> nombresCategorias = categoriaIds.stream()
                .map(categoriaClient::obtenerNombreCategoria)
                .collect(Collectors.toList());
        dto.setCategorias(nombresCategorias);

        // ✅ Imágenes generales
        dto.setImagenes(producto.getImagenes().stream()
                .map(img -> {
                    DetalleProductoDTO.ImagenDTO i = new DetalleProductoDTO.ImagenDTO();
                    i.setUrlImagen(img.getUrlImagen());
                    i.setOrden(img.getOrden());
                    return i;
                }).collect(Collectors.toList()));

        // ✅ Especificaciones
        dto.setEspecificaciones(producto.getEspecificaciones().stream()
                .map(esp -> {
                    DetalleProductoDTO.EspecificacionDTO e = new DetalleProductoDTO.EspecificacionDTO();
                    e.setClave(esp.getClave());
                    e.setValor(esp.getValor());
                    return e;
                }).collect(Collectors.toList()));

        // ✅ Combinaciones
        List<DetalleProductoDTO.CombinacionDTO> combinacionesDTO = new ArrayList<>();
        for (ProductoCombinacion comb : producto.getCombinaciones()) {
            DetalleProductoDTO.CombinacionDTO combDTO = new DetalleProductoDTO.CombinacionDTO();
            combDTO.setPrecio(comb.getPrecio());
            combDTO.setStock(comb.getStock());
            combDTO.setEsPrincipal(comb.isEsPrincipal());

            // Atributos (usando claves locales)
            List<DetalleProductoDTO.AtributoDTO> atributos = comb.getAtributos().stream()
                    .map(a -> {
                        DetalleProductoDTO.AtributoDTO atr = new DetalleProductoDTO.AtributoDTO();
                        atr.setClaveNombre(a.getClave().getClave());
                        atr.setValorNombre(a.getValor().getValor());

                        return atr;
                    }).collect(Collectors.toList());
            combDTO.setAtributos(atributos);

            // Imágenes por combinación
            List<DetalleProductoDTO.ImagenDTO> imgs = comb.getImagenes().stream()
                    .map(img -> {
                        DetalleProductoDTO.ImagenDTO i = new DetalleProductoDTO.ImagenDTO();
                        i.setUrlImagen(img.getUrlImagen());
                        i.setOrden(img.getOrden());
                        return i;
                    }).collect(Collectors.toList());
            combDTO.setImagenes(imgs);

            combinacionesDTO.add(combDTO);
        }
        dto.setCombinaciones(combinacionesDTO);

        // ✅ Precio principal
        // ✅ Precio principal
        producto.getCombinaciones().stream()
                .filter(ProductoCombinacion::isEsPrincipal)
                .findFirst()
                .ifPresent(p -> dto.setPrecio(p.getPrecio()));

// ✅ Imagen principal
        producto.getCombinaciones().stream()
                .filter(ProductoCombinacion::isEsPrincipal)
                .findFirst()
                .flatMap(pc -> pc.getImagenes().stream().findFirst())
                .map(img -> img.getUrlImagen())
                .ifPresent(dto::setUrlImagenPrincipal);

        return dto;

    }







}

