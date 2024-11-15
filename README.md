﻿# ScreenBook

ScreenBook es una aplicación en Java que permite consultar libros y autores a través de una API pública. Los usuarios pueden buscar libros por título, mostrar los libros registrados, listar autores, filtrar libros por idioma y consultar autores que vivieron en un año específico. Los datos se guardan en una base de datos local.

![ScreenBook](images/image.png)

## Funcionalidades

1. **Buscar título de libro**: Permite al usuario buscar libros por su título.
2. **Mostrar los libros guardados**: Muestra todos los libros almacenados en la base de datos local.
3. **Mostrar los autores registrados**: Muestra todos los autores que han sido registrados en la base de datos.
4. **Buscar autores que vivían en un año determinado**: Permite buscar autores que estuvieron vivos en un año específico.
5. **Mostrar libros por idioma**: Filtra los libros almacenados por idioma (Español, Inglés, Francés, Portugués).

## Tecnologías utilizadas

- **Java**: Lenguaje de programación utilizado para desarrollar la aplicación.
- **Spring Data**: Para la gestión de la base de datos y repositorios.
- **Consumo de API REST**: Uso de una API pública para obtener información sobre libros y autores.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes programas:

- **JDK 11 o superior**: Para compilar y ejecutar la aplicación.
- **Maven**: Para gestionar las dependencias del proyecto.

## Instalación

1. Clona el repositorio en tu máquina local:
    ```bash
    git clone https://github.com/tu_usuario/screenbook.git
    ```
2. Entra en el directorio del proyecto:
    ```bash
    cd screenbook
    ```
3. Compila el proyecto con Maven:
    ```bash
    mvn clean install
    ```
4. Ejecuta la aplicación:
    ```bash
    mvn exec:java
    ```

## Uso

1. Al ejecutar la aplicación, verás un menú interactivo con las siguientes opciones:
    - Buscar título de libro
    - Mostrar los libros guardados
    - Mostrar los autores registrados
    - Buscar autores que vivían en un año determinado
    - Mostrar libros por idioma
    - Salir del programa

2. Sigue las instrucciones del menú para realizar las acciones deseadas.


