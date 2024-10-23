package com.example.project.management.mapper;

import com.example.project.management.controller.request.NewPersonalRequest;
import com.example.project.management.controller.response.PersonalResponse;
import com.example.project.management.model.Personal;

public class PersonalMapper {

    public static PersonalResponse toPersonalResponse(Personal personal){
        //creo la respuesta con los datos necesarios
        return new PersonalResponse(personal.getId(), personal.getNombre(), personal.getEstado());
    }

    public static Personal toEntity(NewPersonalRequest newPersonalRequest){
        //creo la entidad con los datos que me pasó el usuario
        //no estoy pasando el estado (visible o no)
        //ya que este no se ingresa por el usuario
        return Personal.builder()
                .nombre(newPersonalRequest.nombre())
                .build();
        //al momento de construir, "@Builder.Default" se encarga de los atributos que no son pasados
        //en este caso, "visible" queda en "true" aunque nunca se pasa
    }

}
