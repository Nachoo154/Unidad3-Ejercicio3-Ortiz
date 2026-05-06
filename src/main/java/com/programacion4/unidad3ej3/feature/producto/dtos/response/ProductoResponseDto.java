package com.programacion4.unidad3ej3.feature.producto.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto {

    private Long id;

    private String nombre;

    private String codigo;

    private String descripcion;

    private Double precio;

    private Integer stock;

    }
