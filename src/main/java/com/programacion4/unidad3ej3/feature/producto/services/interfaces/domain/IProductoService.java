package com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain;

import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoCreateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoPatchRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoUpdateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;

import java.util.List;

public interface IProductoService {

    ProductoResponseDto create(ProductoCreateRequestDto dto);

    List<ProductoResponseDto> getAll();

    ProductoResponseDto getById(Long id);

    ProductoResponseDto update(Long id, ProductoUpdateRequestDto dto);

    ProductoResponseDto patch(Long id, ProductoPatchRequestDto dto);

    void delete(Long id);
}
