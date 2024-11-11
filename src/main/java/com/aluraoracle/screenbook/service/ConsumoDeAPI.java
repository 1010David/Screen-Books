package com.aluraoracle.screenbook.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoDeAPI {

    // Método que obtiene datos de una URL proporcionada
    public String obtenerDatos(String url){
        // Crea un cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Crea una solicitud HTTP para la URL proporcionada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))  // Establece la URI de la solicitud
                .build();

        HttpResponse<String> response = null;

        try {
            // Envía la solicitud HTTP y obtiene la respuesta en forma de String
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e); // Maneja posibles excepciones de IO
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Maneja posibles excepciones de interrupción
        }

        // Devuelve el cuerpo de la respuesta (contenido de la API en formato String)
        String json = response.body();
        return json;
    }
}
