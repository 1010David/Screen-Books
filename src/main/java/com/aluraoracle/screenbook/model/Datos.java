package com.aluraoracle.screenbook.model;

// Importa las anotaciones de Jackson para controlar cómo se serializan y deserializan los objetos JSON.
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Anotación para ignorar cualquier propiedad desconocida en el JSON que no tenga un campo correspondiente en esta clase.
// Evita errores si el JSON contiene propiedades adicionales.
@JsonIgnoreProperties(ignoreUnknown = true)

// Define un registro (record) llamado `Datos` que contiene un solo campo, `resultados`.
// Los registros son una forma compacta en Java de definir clases inmutables con constructores, `equals`, `hashCode` y `toString` generados automáticamente.
public record Datos (

        // Usa `@JsonAlias` para mapear el campo `resultados` con el nombre "results" en el JSON.
        // Esto permite que cuando Jackson procesa un JSON con la propiedad "results", se asigne al campo `resultados`.
        @JsonAlias("results") List<DatosLibros> resultados

) {
}
