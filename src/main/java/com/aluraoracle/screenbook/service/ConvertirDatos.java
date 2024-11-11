package com.aluraoracle.screenbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvertirDatos {

    private ObjectMapper objectMapper = new ObjectMapper(); // Crea un objeto de tipo ObjectMapper

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Convierte el JSON a la clase indicada
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Lanza una excepción en caso de error de procesamiento JSON
        }
    }
}
