package com.programacion4.unidad3ej3.feature.producto.services.impl.domain;

import com.programacion4.unidad3ej3.config.exceptions.ResourceConflictException;
import com.programacion4.unidad3ej3.config.exceptions.ResourceNotFoundException;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoCreateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoPatchRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoUpdateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.mappers.ProductoMapper;
import com.programacion4.unidad3ej3.feature.producto.models.Producto;
import com.programacion4.unidad3ej3.feature.producto.repositories.IProductoRepository;
import com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ProductoService implements IProductoService {

    private final IProductoRepository productoRepository;

    @Override
    public ProductoResponseDto create(ProductoCreateRequestDto dto) {
        String nombreCapitalizado = capitalizar(dto.getNombre());
        if (productoRepository.existsByNombre(nombreCapitalizado)) {
            throw new ResourceConflictException("El nombre del producto ya existe");
        }

        Producto producto = ProductoMapper.toEntity(dto);
        producto.setNombre(nombreCapitalizado);
        producto.setDescripcion(capitalizar(dto.getDescripcion()));
        producto.setEstaEliminado(false);

        return ProductoMapper.toResponseDto(productoRepository.save(producto));
    }

    @Override
    public List<ProductoResponseDto> getAll() {
        return productoRepository.findByEstaEliminadoFalse()
                .stream()
                .map(ProductoMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductoResponseDto getById(Long id) {
        Producto producto = getProductoNoEliminado(id);
        return ProductoMapper.toResponseDto(producto);
    }

    @Override
    public ProductoResponseDto update(Long id, ProductoUpdateRequestDto dto) {
        Producto producto = getProductoNoEliminado(id);
        producto.setNombre(capitalizar(dto.getNombre()));
        producto.setCodigo(dto.getCodigo());
        producto.setDescripcion(capitalizar(dto.getDescripcion()));
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        return ProductoMapper.toResponseDto(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDto patch(Long id, ProductoPatchRequestDto dto) {
        Producto producto = getProductoNoEliminado(id);

        if (dto.getPrecio() != null) {
            producto.setPrecio(dto.getPrecio());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }

        return ProductoMapper.toResponseDto(productoRepository.save(producto));
    }

    @Override
    public void delete(Long id) {
        Producto producto = getProductoNoEliminado(id);
        producto.setEstaEliminado(true);
        productoRepository.save(producto);
    }

    private Producto getProductoNoEliminado(Long id) {
        return productoRepository.findByIdAndEstaEliminadoFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return texto;
        }
        String limpio = texto.trim().toLowerCase(Locale.ROOT);
        return limpio.substring(0, 1).toUpperCase(Locale.ROOT) + limpio.substring(1);
    }
}
