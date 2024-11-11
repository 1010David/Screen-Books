package com.aluraoracle.screenbook.repository;


import com.aluraoracle.screenbook.model.IdiomaDeLibro;
import com.aluraoracle.screenbook.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros, Long> {

    Optional<Libros> findByTituloIgnoreCase(String titulo);

    List<Libros> findByIdioma(IdiomaDeLibro idioma);
}