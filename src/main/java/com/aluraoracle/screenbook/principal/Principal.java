package com.aluraoracle.screenbook.principal;

// Importación de clases necesarias para el funcionamiento de la aplicación
import com.aluraoracle.screenbook.model.*;
import com.aluraoracle.screenbook.repository.AutoresRepository;
import com.aluraoracle.screenbook.repository.LibroRepository;
import com.aluraoracle.screenbook.service.ConsumoDeAPI;
import com.aluraoracle.screenbook.service.ConvertirDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    // Scanner para leer la entrada del usuario desde la consola
    private Scanner teclado = new Scanner(System.in);

    // URL base para la API desde donde se obtendrán datos de libros
    private static final String URL_BASE = "https://gutendex.com/books/";

    // Inicialización de servicios para consumir API y convertir datos obtenidos
    private ConsumoDeAPI consumoApi = new ConsumoDeAPI();
    private ConvertirDatos conversor = new ConvertirDatos();

    // Repositorios para almacenar y gestionar autores y libros en la base de datos
    private AutoresRepository autoresRespositorio;
    private LibroRepository libroRepositorio;

    // Constructor que recibe repositorios de autores y libros como dependencias
    public Principal(AutoresRepository autor, LibroRepository libro) {
        this.autoresRespositorio = autor;
        this.libroRepositorio = libro;
    }

    // Método para mostrar el menú principal y manejar las opciones seleccionadas por el usuario
    public void mostrarElMenu() {
        // Llama al servicio para obtener datos en formato JSON desde la URL base
        var json = consumoApi.obtenerDatos(URL_BASE);
        int opcion;

        // Bucle do-while que se ejecuta hasta que el usuario elige salir con la opción 0
        do {
            // Menú principal mostrado en consola con varias opciones para el usuario
            System.out.println("""
                     ------------------------------
                    1.- Buscar titulo de Libro
                    2.- Mostrar los libros guardados
                    3.- Mostrar los autores registrados
                    4.- Buscar autores que vivian en un año determinado
                    5.- Mostrar libros por idioma
                    0.- Salir del Programa
                    """);
            System.out.print("Opción: ");
            opcion = teclado.nextInt();  // Lee la opción seleccionada por el usuario
            teclado.nextLine();  // Limpia el buffer de entrada

            // Estructura switch para manejar cada opción del menú
            switch (opcion) {
                case 1 -> buscarLibrosPorNombre();  // Llama al método para buscar libros por título
                case 2 -> mostrarLosLibrosRegistrados();  // Muestra todos los libros registrados
                case 3 -> listaAutoresRegistrados();  // Muestra todos los autores registrados
                case 4 -> AutoresVivosEnDeterminadoTiempo();  // Muestra autores que vivieron en un año específico
                case 5 -> listarLibrosPorIdiomas();  // Muestra libros según el idioma seleccionado
                case 0 -> System.out.println("Terminando...");  // Mensaje de salida
                default -> System.out.println("\nOpción no válida\n");  // Manejo de opción inválida
            }
        } while (opcion != 0);  // El bucle continúa hasta que se elige la opción 0
    }


    private void buscarLibrosPorNombre() {
        // Solicita al usuario el título del libro que desea buscar
        System.out.println("Nombre del libro por buscar");
        var tituloLibro = teclado.nextLine();

        // Construye la URL para la búsqueda en la API, reemplazando espacios con '+'
        String url = URL_BASE + "?search=" + tituloLibro.replace(" ", "+");

        // Llama al servicio de API para obtener datos en formato JSON de la URL construida
        String json = consumoApi.obtenerDatos(url);

        // Convierte el JSON a un objeto Datos, que contiene la lista de resultados
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        // Filtra los resultados buscando un libro cuyo título coincida exactamente
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados()
                .stream()
                .filter(l -> l.titulo().equalsIgnoreCase(tituloLibro))
                .findFirst();

        // Si el libro está presente en los resultados, procede con el registro en la base de datos
        if (libroBuscado.isPresent()) {
            DatosLibros datoslibroEcontrado = libroBuscado.get();
            DatosAutores datosAutor = datoslibroEcontrado.autor().get(0);  // Obtiene el primer autor del libro

            // Busca si el autor ya está registrado en la base de datos. Si no está, lo guarda
            Autores autor = autoresRespositorio.findByNombreIgnoreCase(datosAutor.nombre()).orElseGet(() -> {
                Autores nuevoAutor = new Autores(datosAutor);
                return autoresRespositorio.save(nuevoAutor);
            });

            // Verifica si el libro ya existe en la base de datos
            if (libroRepositorio.findByTituloIgnoreCase(datoslibroEcontrado.titulo()).isPresent()) {
                System.out.println("\nEl libro ya está registrado, pruebe con otro libro\n");
            } else {
                // Si el libro no existe, lo registra, asigna su autor y lo guarda en la base de datos
                Libros libroEcontrado = new Libros(datoslibroEcontrado);
                libroEcontrado.setAutores(autor);
                libroRepositorio.save(libroEcontrado);
                System.out.println(libroEcontrado + "\nLibro registrado exitosamente\n");
            }
        } else {
            // Si no se encontró el libro, muestra un mensaje al usuario
            System.out.println("\nLibro no encontrado, pruebe con otro libro\n");
        }
    }

    private void mostrarLosLibrosRegistrados() {
        // Obtiene todos los libros registrados en la base de datos
        List<Libros> libros = libroRepositorio.findAll();

        // Verifica si la lista de libros está vacía
        if (libros.isEmpty()) {
            System.out.println("\nBase sin datos");
        } else {
            // Si hay libros registrados, los muestra en la consola
            libros.forEach(System.out::println);
        }
    }

    private void listaAutoresRegistrados() {
        // Obtiene todos los autores registrados en la base de datos
        List<Autores> autores = autoresRespositorio.findAll();

        // Verifica si la lista de autores está vacía
        if (autores.isEmpty()) {
            System.out.println("\nBase sin datos");
        } else {
            // Si hay autores registrados, los muestra en la consola
            autores.forEach(System.out::println);
        }
    }

    private void AutoresVivosEnDeterminadoTiempo() {
        // Solicita al usuario que ingrese el año para buscar autores que vivían en esa época
        System.out.println("Año del autor vivo");
        try {
            int anio = teclado.nextInt();  // Lee el año ingresado
            teclado.nextLine();  // Limpia el buffer de entrada

            // Obtiene los autores que vivían en el año especificado
            List<Autores> autoresVivos = autoresRespositorio.autoresVivosEnUnDeterminadoAnio(anio);

            // Verifica si no hay autores en ese año
            if (autoresVivos.isEmpty()) {
                System.out.println("\nNo hay autores vivos en " + anio);
            } else {
                // Muestra los autores encontrados
                autoresVivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            // Muestra un mensaje en caso de que la entrada no sea válida
            System.out.println("\nNo válido");
            teclado.nextLine();  // Limpia el buffer de entrada para evitar errores futuros
        }
    }

    private void listarLibrosPorIdiomas() {
        // Solicita al usuario el idioma en el que buscar los libros
        System.out.println("Idioma en el cual buscar los libros");
        int opcion;

        // Bucle para mostrar las opciones de idiomas hasta que el usuario elija salir
        do {
            System.out.println("""
                    1 - Español
                    2 - Inglés
                    3 - Francés
                    4 - Portugués
                    0 - Salir
                    """);
            opcion = teclado.nextInt();
            teclado.nextLine();

            // Switch con expresión `yield` para filtrar libros por idioma seleccionado
            List<Libros> librosPorIdioma = switch (opcion) {
                case 1 -> libroRepositorio.findByIdioma(IdiomaDeLibro.ES);
                case 2 -> libroRepositorio.findByIdioma(IdiomaDeLibro.EN);
                case 3 -> libroRepositorio.findByIdioma(IdiomaDeLibro.FR);
                case 4 -> libroRepositorio.findByIdioma(IdiomaDeLibro.PT);
                default -> {
                    System.out.println("\nOpción no válida");
                    yield List.of();  // Retorna una lista vacía en caso de opción no válida
                }
            };

            // Verifica si no hay libros en el idioma seleccionado
            if (librosPorIdioma.isEmpty()) {
                System.out.println("\nNo hay libros en el idioma seleccionado");
            } else {
                // Muestra los libros en el idioma seleccionado
                librosPorIdioma.forEach(System.out::println);
            }
        } while (opcion != 0);  // Sale del bucle si el usuario elige 0
    }
}