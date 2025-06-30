package com.msproductos.service.impl;

import com.msproductos.dto.ProductoCombinacionDTO;
import com.msproductos.entities.CombinacionAtributo;
import com.msproductos.entities.ImagenCombinacion;
import com.msproductos.entities.ProductoCombinacion;
import com.msproductos.repository.CombinacionAtributoRepository;
import com.msproductos.repository.ProductoCombinacionRepository;
import com.msproductos.repository.ProductoUsuarioRepository;
import com.msproductos.service.ProductoCombinacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoCombinacionServiceImpl implements ProductoCombinacionService {

    private final ProductoCombinacionRepository productoCombinacionRepository;
    private final ProductoUsuarioRepository productoUsuarioRepository;

    @Autowired
    private CombinacionAtributoRepository combinacionAtributoRepository;


    @Override
    public ProductoCombinacionDTO obtenerPorId(UUID idCombinacion) {
        ProductoCombinacion combinacion = productoCombinacionRepository.findById(idCombinacion)
                .orElseThrow(() -> new RuntimeException("❌ Combinación no encontrada"));

        ProductoCombinacionDTO dto = new ProductoCombinacionDTO();
        dto.setIdCombinacion(combinacion.getIdCombinacion());
        dto.setPrecio(combinacion.getPrecio());
        dto.setStock(combinacion.getStock());

        // ✅ CLAVE: poner el productoId base!
        dto.setProductoId(combinacion.getProducto().getId());  // ⭐⭐⭐ Aquí arreglas todo

        String nombreProducto = combinacion.getProducto().getNombre();
        dto.setNombre(nombreProducto);

        List<CombinacionAtributo> atributos = combinacionAtributoRepository.findByCombinacion(combinacion);

        if (atributos.size() != 2) {
            throw new RuntimeException("La combinación debe tener exactamente 2 claves.");
        }

        dto.setValorClave1(atributos.get(0).getValor().getValor());
        dto.setValorClave2(atributos.get(1).getValor().getValor());

        dto.setImagenes(
                combinacion.getImagenes().stream()
                        .sorted(Comparator.comparing(ImagenCombinacion::getOrden))
                        .map(ImagenCombinacion::getUrlImagen)
                        .collect(Collectors.toList())
        );

        return dto;
    }


    @Override
    public ProductoCombinacionDTO obtenerStockPorCombinacion(UUID combinacionId) {
        // Buscar la combinación por su UUID
        ProductoCombinacion combinacion = productoCombinacionRepository.findById(combinacionId)
                .orElseThrow(() -> new RuntimeException("❌ Combinación no encontrada"));

        // Devolver el DTO con el stock de la combinación
        ProductoCombinacionDTO dto = new ProductoCombinacionDTO();
        dto.setIdCombinacion(combinacion.getIdCombinacion());
        dto.setStock(combinacion.getStock());
        dto.setPrecio(combinacion.getPrecio());
        dto.setNombre(combinacion.getProducto().getNombre()); // Asegúrate de agregar el nombre del producto si lo necesitas
        dto.setImagenes(combinacion.getImagenes().stream().map(img -> img.getUrlImagen()).toList());

        return dto;
    }







}
