package com.aluraoracle.screenbook.model;

import jakarta.persistence.*;

// Define la clase `Libros` como una entidad mapeada a la tabla "libros" en la base de datos.
@Entity
@Table(name = "libros")
public class Libros {

    // Define el campo `id` como la clave primaria, con generación automática de valores.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define el campo `titulo` como una columna única en la tabla "libros".
    @Column(unique = true)
    private String titulo;

    // Constructor que inicializa `Libros` usando un objeto `DatosLibros`.
    // Asigna `titulo`, `idioma` (convirtiendo la cadena de idioma desde la API) y `numeroDescargas`.
    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idioma = IdiomaDeLibro.fromString(datosLibros.idiomas().toString().split(",")[0]);
        this.numeroDescargas = datosLibros.numeroDeDescargas();
    }

    // Sobrescribe el método `toString` para mostrar información detallada de un libro.
    @Override
    public String toString() {
        return "\n_                  _BOOK_                  _\n" +
                " Titulo: '" + titulo + "\n" +
                " Autor: " + autores.getNombre() + "\n" +
                " Idioma: " + idioma + "\n" +
                " Numero Descargas: " + numeroDescargas + "\n" +
                "_____________________________________________\n";
    }

    // Define el campo `idioma` como una enumeración `IdiomaDeLibro`, almacenada en la base de datos como una cadena.
    @Enumerated(EnumType.STRING)
    private IdiomaDeLibro idioma;

    // Campo que almacena el número de descargas del libro.
    private double numeroDescargas;

    // Relación `ManyToOne` que conecta cada libro con un único autor.
    @ManyToOne
    private Autores autores;

    // Métodos getter y setter para acceder y modificar los campos privados de la clase.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autores getAutores() {
        return getAutores();
    }

    public void setAutores(Autores autor) {
        this.autores = autor;
    }

    public IdiomaDeLibro getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaDeLibro idioma) {
        this.idioma = idioma;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Libros(Autores autores) {
        this.autores = autores;
    }
    // Constructor vacío necesario para Hibernate
    public Libros() {
    }

    public Libros(Long id, String titulo, IdiomaDeLibro idioma, double numeroDescargas, Autores autores) {
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autores = autores;
    }
}
