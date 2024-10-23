package com.example.project.management.controller.response;

public record PersonalResponse(Integer id, String nombre, int estado) {
    //voy a pasar todos los parametros que quiera mostrar (en este caso: id, nombre y estado)
}
