package com.aluraoracle.screenbook.model;

// Define una enumeración `IdiomaDeLibro` para representar varios idiomas de los libros.
public enum IdiomaDeLibro {

    // Cada constante enum representa un idioma con dos atributos:
    // `idiomaApi` (formato en la API) y `idioma` (nombre del idioma en español).
    ES("[es]", "Español"),
    EN("[en]", "Inglés"),
    FR("[fr]", "Francés"),
    PT("[pt]", "Portugués");

    // Campos privados para almacenar los valores de idioma de la API y en español.
    private final String idiomaApi;
    private final String idioma;

    // Constructor para inicializar cada constante enum con valores específicos.
    IdiomaDeLibro(String idiomaApi, String idioma) {
        this.idiomaApi = idiomaApi;
        this.idioma = idioma;
    }

    // Método estático para obtener un `IdiomaDeLibro` basado en el valor `idiomaApi` de la API.
    // Recorre cada valor en el enum para encontrar una coincidencia ignorando mayúsculas/minúsculas.
    public static IdiomaDeLibro fromString(String idioma) {
        for (IdiomaDeLibro i : IdiomaDeLibro.values()) {
            if (i.idiomaApi.equalsIgnoreCase(idioma)) {
                return i;
            }
        }
        // Lanza una excepción si no se encuentra un idioma que coincida con el valor proporcionado.
        throw new IllegalArgumentException("Ningún idioma encontrado: " + idioma);
    }

    // Método estático para obtener un `IdiomaDeLibro` a partir del nombre del idioma en español.
    // Devuelve `null` si no encuentra un idioma que coincida con el valor proporcionado.
    public static IdiomaDeLibro fromEspanol(String idioma) {
        for (IdiomaDeLibro i : IdiomaDeLibro.values()) {
            if (i.idioma.equals(idioma)) {
                return i;
            }
        }
        return null;  // Retorna null si no se encuentra el idioma.
    }
}
