package com.programacion4.unidad3ej3.feature.producto.controllers;

import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoCreateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoPatchRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoUpdateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain.IProductoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDto> create(@Valid @RequestBody ProductoCreateRequestDto dto) {
        return new ResponseEntity<>(productoService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> getAll() {
        return new ResponseEntity<>(productoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(productoService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> update(@PathVariable Long id,
                                                      @Valid @RequestBody ProductoUpdateRequestDto dto) {
        return new ResponseEntity<>(productoService.update(id, dto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> patch(@PathVariable Long id,
                                                     @RequestBody ProductoPatchRequestDto dto) {
        return new ResponseEntity<>(productoService.patch(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
