package com.example.project.management.service;

import java.util.List;

import com.example.project.management.controller.request.NewPersonalRequest;
import com.example.project.management.controller.response.PersonalResponse;
import com.example.project.management.model.Personal;
public interface IPersonalService {

    List<PersonalResponse> listar(); //listo de forma automática todos los personales no borrados
    Personal buscarPorId(Integer id);
    Personal buscarPorNombre (String nombre);

    //guardamos la entidad, pero aplicando request y response
    PersonalResponse crear(NewPersonalRequest newPersonalRequest);
    PersonalResponse actualizar(NewPersonalRequest newPersonalRequest, Integer id);

    void eliminar(Personal model);
    void recuperar (Personal model);
}
