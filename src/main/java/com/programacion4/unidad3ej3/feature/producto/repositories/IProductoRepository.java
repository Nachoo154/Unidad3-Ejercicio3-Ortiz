package com.programacion4.unidad3ej3.feature.producto.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.programacion4.unidad3ej3.feature.producto.models.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, Long> {

    List<Producto> findByEstaEliminadoFalse();

    Optional<Producto> findByIdAndEstaEliminadoFalse(Long id);

    boolean existsByNombre(String nombre);
    
}
