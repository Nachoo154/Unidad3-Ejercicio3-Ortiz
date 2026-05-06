package com.programacion4.unidad3ej3.feature.producto;

import com.programacion4.unidad3ej3.config.exceptions.ResourceConflictException;
import com.programacion4.unidad3ej3.config.exceptions.ResourceNotFoundException;
import com.programacion4.unidad3ej3.feature.producto.controllers.ProductoController;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoCreateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoPatchRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.request.ProductoUpdateRequestDto;
import com.programacion4.unidad3ej3.feature.producto.dtos.response.ProductoResponseDto;
import com.programacion4.unidad3ej3.feature.producto.models.Producto;
import com.programacion4.unidad3ej3.feature.producto.repositories.IProductoRepository;
import com.programacion4.unidad3ej3.feature.producto.services.interfaces.domain.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoCrudFlowTest {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ProductoController productoController;

    @Autowired
    private IProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
    }

    @Test
    void us01_create_conflictoYCapitalizacion() {
        productoService.create(new ProductoCreateRequestDto("Remera", "R0", "Base", 1000.0, 5));

        assertThrows(ResourceConflictException.class, () ->
                productoService.create(new ProductoCreateRequestDto("remera", "R1", "otra", 1200.0, 3))
        );

        ProductoResponseDto creado = productoService.create(
                new ProductoCreateRequestDto("buzo DE INVIERNO", "B1", "ABRIGADO TOTAL", 3000.0, 9)
        );

        assertNotNull(creado.getId());
        assertEquals("Buzo de invierno", creado.getNombre());
        assertEquals("Abrigado total", creado.getDescripcion());

        Producto entity = productoRepository.findById(creado.getId()).orElseThrow();
        assertFalse(entity.isEstaEliminado());
    }

    @Test
    void us02_getAll_listaVaciaYNoEliminados() {
        List<ProductoResponseDto> vacio = productoService.getAll();
        assertTrue(vacio.isEmpty());

        productoRepository.save(new Producto(null, "A", "A1", "A", 1.0, 1, false));
        productoRepository.save(new Producto(null, "B", "B1", "B", 2.0, 2, true));

        List<ProductoResponseDto> lista = productoService.getAll();
        assertEquals(1, lista.size());
        assertEquals("A", lista.get(0).getNombre());
    }

    @Test
    void us03_getById_noExiste() {
        assertThrows(ResourceNotFoundException.class, () -> productoService.getById(999L));
    }

    @Test
    void us04_put_actualizacionTotal() {
        ProductoResponseDto base = productoService.create(
                new ProductoCreateRequestDto("campera", "C1", "invierno", 5000.0, 4)
        );

        ProductoResponseDto actualizado = productoService.update(
                base.getId(),
                new ProductoUpdateRequestDto("campera NUEVA", "C2", "NUEVA TEMPORADA", 7500.0, 10)
        );

        assertEquals("Campera nueva", actualizado.getNombre());
        assertEquals("C2", actualizado.getCodigo());
        assertEquals("Nueva temporada", actualizado.getDescripcion());
        assertEquals(7500.0, actualizado.getPrecio());
        assertEquals(10, actualizado.getStock());
    }

    @Test
    void us05_patch_actualizacionParcial() {
        ProductoResponseDto base = productoService.create(
                new ProductoCreateRequestDto("gorra", "G1", "negra", 1000.0, 3)
        );

        ProductoResponseDto parcheado = productoService.patch(base.getId(), new ProductoPatchRequestDto(null, 99));

        assertEquals(1000.0, parcheado.getPrecio());
        assertEquals(99, parcheado.getStock());
        assertEquals("Gorra", parcheado.getNombre());
    }

    @Test
    void us06_delete_softDeleteYNotFoundLuego() {
        ProductoResponseDto base = productoService.create(
                new ProductoCreateRequestDto("zapatillas", "Z1", "running", 9000.0, 2)
        );

        assertEquals(HttpStatus.NO_CONTENT, productoController.delete(base.getId()).getStatusCode());

        Producto entity = productoRepository.findById(base.getId()).orElseThrow();
        assertTrue(entity.isEstaEliminado());

        assertThrows(ResourceNotFoundException.class, () -> productoService.getById(base.getId()));
    }
}
