package com.aluraoracle.screenbook.model;

// Importa las anotaciones de Jackson para configurar el mapeo de propiedades JSON a campos de esta clase.
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Anotación para ignorar cualquier propiedad desconocida en el JSON que no tenga un campo correspondiente en esta clase.
// Esto ayuda a evitar errores si el JSON contiene propiedades adicionales no usadas en este modelo.
@JsonIgnoreProperties(ignoreUnknown = true)

// Define un registro (record) llamado `DatosAutores` que representa los datos del autor.
// Los registros en Java son una forma concisa de definir clases inmutables con campos y métodos como `equals` y `toString` generados automáticamente.
public record DatosAutores(

        // Usa `@JsonAlias` para mapear el campo `nombre` con el nombre "name" en el JSON.
        @JsonAlias("name") String nombre,

        // Mapea el campo `fechaDeNacimiento` con la propiedad "birth_year" del JSON.
        @JsonAlias("birth_year") int fechaDeNacimiento,

        // Mapea el campo `fechaDeMuerte` con la propiedad "death_year" del JSON.
        @JsonAlias("death_year") int fechaDeMuerte

) {
}
