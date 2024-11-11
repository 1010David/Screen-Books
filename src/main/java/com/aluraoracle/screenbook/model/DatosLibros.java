package com.aluraoracle.screenbook.model;

// Importa las anotaciones de Jackson para configurar el mapeo de propiedades JSON a los campos de esta clase.
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Anotación para ignorar propiedades desconocidas en el JSON que no tengan un campo correspondiente en esta clase.
// Esto permite evitar errores si el JSON contiene propiedades adicionales que no se utilizan en este modelo.
@JsonIgnoreProperties(ignoreUnknown = true)

// Define un registro (record) llamado `DatosLibros`, que representa los datos de un libro.
// Los registros en Java son una forma concisa de definir clases inmutables con campos y métodos generados automáticamente.
public record DatosLibros(

        // Mapea el campo `titulo` con la propiedad "title" en el JSON.
        @JsonAlias("title") String titulo,

        // Mapea el campo `autor` con la propiedad "authors" en el JSON, que es una lista de `DatosAutores`.
        @JsonAlias("authors") List<DatosAutores> autor,

        // Mapea el campo `idiomas` con la propiedad "languages" en el JSON, que es una lista de strings.
        @JsonAlias("languages") List<String> idiomas,

        // Mapea el campo `numeroDeDescargas` con la propiedad "download_count" en el JSON.
        @JsonAlias("download_count") Double numeroDeDescargas
) {
}
