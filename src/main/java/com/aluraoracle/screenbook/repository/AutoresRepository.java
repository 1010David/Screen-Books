package com.aluraoracle.screenbook.repository;

import com.aluraoracle.screenbook.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
    // Encuentra un autor por su nombre, ignorando mayúsculas y minúsculas
    Optional<Autores> findByNombreIgnoreCase(String nombre);

    // Consulta personalizada para encontrar autores que estaban vivos en un determinado año
    @Query("SELECT a FROM Autores a WHERE a.fechaDeNacimiento <= :anio AND a.fechaDeMuerte >= :anio")
    List<Autores> autoresVivosEnUnDeterminadoAnio(@Param("anio") Integer anio);
}
