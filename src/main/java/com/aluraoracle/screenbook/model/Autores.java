package com.aluraoracle.screenbook.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

// Indica que esta clase es una entidad JPA, lo cual significa que se mapeará a una tabla en la base de datos.
@Entity
// Especifica que la tabla en la base de datos se llamará "autores".
@Table(name = "autores")
public class Autores {

    // Define el atributo 'id' como la clave primaria de esta entidad con generación automática de valores únicos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Columna única en la base de datos para almacenar el nombre del autor.
    @Column(unique = true)
    private String nombre;

    // Atributos para almacenar las fechas de nacimiento y muerte del autor como años enteros.
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;

    // Relación uno-a-muchos con la entidad 'Libros', ya que un autor puede tener múltiples libros.
    // 'mappedBy' hace referencia al atributo en la entidad 'Libros' que se relaciona con esta entidad.
    // 'cascade' permite que las operaciones de la entidad se propaguen a los libros relacionados.
    // 'fetch = FetchType.EAGER' indica que se cargará la relación completamente cuando se cargue un Autor.
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

    // Constructor vacío requerido por JPA.
    public Autores() {
    }

    // Constructor que inicializa un Autor a partir de un objeto 'DatosAutores'.
    public Autores(DatosAutores datosAuthor) {
        this.nombre = datosAuthor.nombre();
        this.fechaDeNacimiento = datosAuthor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAuthor.fechaDeMuerte();
    }

    // Método 'toString' para obtener una representación en texto del objeto Autor.
    // Genera una lista de nombres de los libros asociados y la incluye en la cadena de texto.
    @Override
    public String toString() {
        String librosNombres = (libros == null || libros.isEmpty())
                ? "No tiene libros asociados"
                : libros.stream()
                .map(Libros::getTitulo)
                .collect(Collectors.joining(", "));

        return "-------------Autor-------------\n" +
                " Nombre: " + nombre + "\n" +
                " Fecha De Nacimiento: " + (fechaDeNacimiento != null ? fechaDeNacimiento : "N/A") + "\n" +
                " Fecha De Muerte: " + (fechaDeMuerte != null ? fechaDeMuerte : "N/A") + "\n" +
                " Libros: " + librosNombres + "\n";
    }


    // Métodos getter y setter para cada atributo de la clase.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }
}
