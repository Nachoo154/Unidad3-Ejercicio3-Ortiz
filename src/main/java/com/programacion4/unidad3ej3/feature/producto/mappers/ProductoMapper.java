package com.programacion4.unidad3ej3.feature.producto.mappers;

import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoCreateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoUpdateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.models.Producto;

public class ProductoMapper {
    
    public static Producto toEntity(ProductoCreateRequestDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCodigo(dto.getCodigo());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }

    public static Producto toEntity(ProductoUpdateRequestDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCodigo(dto.getCodigo());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }

    public static ProductoResponseDto toResponseDto(Producto producto) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCodigo(producto.getCodigo());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        return dto;
    }
}
