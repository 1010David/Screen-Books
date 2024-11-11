package com.aluraoracle.screenbook.service;

// Definición de la interfaz IConvertirDatos
public interface IConvertirDatos {

    // Método genérico para convertir un string JSON en un objeto de la clase especificada
    <T> T obtenerDatos(String json, Class<T> clase);
}
