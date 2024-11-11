package com.aluraoracle.screenbook.principal;

// Importación de las clases necesarias
import com.aluraoracle.screenbook.model.*;
import com.aluraoracle.screenbook.repository.AutoresRepository;
import com.aluraoracle.screenbook.repository.LibroRepository;
import com.aluraoracle.screenbook.service.ConsumoDeAPI;
import com.aluraoracle.screenbook.service.ConvertirDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal{

    // Declaración de objetos y variables
    private Scanner teclado = new Scanner(System.in);  // Para leer entradas desde la consola
    private static final String URL_BASE = "https://gutendex.com/books/";  // URL base para la API
    private ConsumoDeAPI consumoApi = new ConsumoDeAPI();  // Objeto para consumir la API
    private ConvertirDatos conversor = new ConvertirDatos();  // Objeto para convertir datos de la API a clases
    private AutoresRepository autoresRespositorio;  // Repositorio de autores
    private LibroRepository libroRepositorio;  // Repositorio de libros

    // Constructor de la clase Principal que recibe repositorios de autores y libros
    public Principal(AutoresRepository autor, LibroRepository libro) {
        this.autoresRespositorio = autor;
        this.libroRepositorio = libro;
    }

    // Método que muestra el menú principal y maneja la navegación
    public void mostrarElMenu(){
        // Se obtiene la respuesta de la API en formato JSON
        var json = consumoApi.obtenerDatos(URL_BASE);

        int opcion = -1;
        while (opcion != 0) {
            // Se muestra el menú de opciones al usuario
            String menu = """
                    ------------------------------
                   1.- Buscar Libro por titulo
                   2.- Mostrar los libros registrados
                   3.- Mostrar los autores
                   4.- Buscar autores que se encontraban vivos en determinado año
                   5.- Mostrar libros por idioma
                    0.-Salir del Programa
                   """;
            System.out.println( "\n"+ menu);
            System.out.print("Opción: ");
            opcion = teclado.nextInt();  // Se lee la opción del usuario
            teclado.nextLine();  // Limpiar el buffer

            // Se ejecuta el caso correspondiente según la opción seleccionada
            switch (opcion) {
                case 1:
                    buscarLibrosPorNombre();
                    break;
                case 2:
                    mostrarLosLibrosRegistrados();
                    break;
                case 3:
                    listaAutoresRegistrados();
                    break;
                case 4:
                    AutoresVivosEnDeterminadoTiempo();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
                    break;
            }
        }
    }

    // Método para buscar libros por nombre
    private void buscarLibrosPorNombre() {
        System.out.println("Ingrese nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();  // Se lee el título del libro

        // Construcción de la URL para hacer la consulta a la API
        String url = URL_BASE + "?search=" + tituloLibro.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);  // Se obtiene la respuesta de la API
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);  // Se convierten los datos del JSON
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados()
                .stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();  // Se busca el libro por el título

        if (libroBuscado.isPresent()) {
            // Si se encuentra el libro, se procesa la información del autor
            DatosLibros datoslibroEcontrado = libroBuscado.get();
            DatosAutores datosAutor = datoslibroEcontrado.autor().get(0);

            // Se busca el autor en la base de datos o se crea uno nuevo si no existe
            Autores autor = autoresRespositorio.findByNombreIgnoreCase(datosAutor.nombre()).orElseGet(() -> {
                Autores nuevoAutor = new Autores(datosAutor);
                return autoresRespositorio.save(nuevoAutor);
            });

            // Se verifica si el libro ya está registrado
            Optional<Libros> libroExiste = libroRepositorio.findByTituloIgnoreCase(datoslibroEcontrado.titulo());

            if (libroExiste.isPresent()) {
                System.out.println("\nEl libro ya esta registrado, pruebe con otro libro\n");
            } else {
                // Si el libro no existe, se registra el nuevo libro
                Libros libroEcontrado = new Libros(datoslibroEcontrado);
                libroEcontrado.setAutores(autor);
                libroRepositorio.save(libroEcontrado);
                System.out.println(libroEcontrado);
                System.out.println("\nLibro registrado exitosamente\n");
            }
        } else {
            System.out.println("\nLibro no encontrado, pruebe con otro libro\n");
        }
    }

    // Método para mostrar los libros registrados en la base de datos
    private void mostrarLosLibrosRegistrados() {
        List<Libros> libros = libroRepositorio.findAll();  // Se obtienen todos los libros

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
        } else {
            libros.forEach(System.out::println);  // Se muestran los libros
        }
    }

    // Método para mostrar los autores registrados
    private void listaAutoresRegistrados() {
        List<Autores> autores = autoresRespositorio.findAll();  // Se obtienen todos los autores

        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados");
        } else {
            autores.forEach(System.out::println);  // Se muestran los autores
        }
    }

    // Método para buscar autores vivos en un año determinado
    private void AutoresVivosEnDeterminadoTiempo() {
        System.out.println("Ingrese el año en el cual desea buscar autores vivos");

        try {
            int anio = teclado.nextInt();  // Se lee el año
            teclado.nextLine();  // Limpiar el buffer
            List<Autores> autoresVivos = autoresRespositorio.autoresVivosEnUnDeterminadoAnio(anio);  // Se buscan autores vivos en el año
            if (autoresVivos.isEmpty()) {
                System.out.println("\nNo hay autores vivos en el año " + anio);
            } else {
                autoresVivos.forEach(System.out::println);  // Se muestran los autores vivos
            }
        } catch (Exception e) {
            System.out.println("\nAño no válido");
            teclado.nextLine();  // Limpiar el buffer
        }
    }

    // Método para listar libros según su idioma
    private void listarLibrosPorIdiomas() {
        System.out.println("Ingrese el idioma en el cual desea buscar los libros");
        int opcion = -1;
        while (opcion != 0) {
            // Menú de idiomas
            String menuIdiomas = """
                    1 - Español
                    2 - Ingles
                    3 - Frances
                    4 - Portugues
                    0 - Salir
                    """;
            System.out.println(menuIdiomas);

            try {
                opcion = teclado.nextInt();  // Se lee la opción del idioma
            } catch (Exception e) {
                System.out.println("Opción no válida");
                teclado.nextLine();  // Limpiar el buffer
                continue;
            }

            // Según el idioma elegido, se muestran los libros filtrados
            switch (opcion) {
                case 1:
                    List<Libros> librosEspanol = libroRepositorio.findByIdioma(IdiomaDeLibro.ES);
                    if (librosEspanol.isEmpty()) {
                        System.out.println("\nNo hay libros en español");
                    } else {
                        librosEspanol.forEach(System.out::println);  // Mostrar libros en español
                    }
                    break;
                case 2:
                    List<Libros> librosIngles = libroRepositorio.findByIdioma(IdiomaDeLibro.EN);
                    if (librosIngles.isEmpty()) {
                        System.out.println("\nNo hay libros en inglés");
                    } else {
                        librosIngles.forEach(System.out::println);  // Mostrar libros en inglés
                    }
                    break;
                case 3:
                    List<Libros> librosFrances = libroRepositorio.findByIdioma(IdiomaDeLibro.FR);
                    if (librosFrances.isEmpty()) {
                        System.out.println("\nNo hay libros en francés");
                    } else {
                        librosFrances.forEach(System.out::println);  // Mostrar libros en francés
                    }
                    break;
                case 4:
                    List<Libros> librosPortugues = libroRepositorio.findByIdioma(IdiomaDeLibro.PT);
                    if (librosPortugues.isEmpty()) {
                        System.out.println("\nNo hay libros en portugués");
                    } else {
                        librosPortugues.forEach(System.out::println);  // Mostrar libros en portugués
                    }
                    break;
                default:
                    System.out.println("\nOpción no válida");
                    break;
            }
        }
    }
}
