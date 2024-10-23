package com.example.project.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.management.controller.request.NewPersonalRequest;
import com.example.project.management.controller.response.PersonalResponse;
import com.example.project.management.mapper.PersonalMapper;
import com.example.project.management.model.Personal;
import com.example.project.management.repository.IPersonalRepository;

@Service //declaro que la clase es un service

public class PersonalService implements IPersonalService {

    @Autowired
    private IPersonalRepository modelRepository;

    @Override
    public List<PersonalResponse> listar() {
        List<Personal> personales = modelRepository.findByEstado(Personal.COMUN);
        
        return personales.stream().map(PersonalMapper::toPersonalResponse).toList();
    }

    @Override
    public Personal buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Personal buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public PersonalResponse crear(NewPersonalRequest newPersonalRequest) {
        Personal model = PersonalMapper.toEntity(newPersonalRequest); //de peticion a personal
        Optional<Personal> personalOptional = modelRepository.findByNombre(model.getNombre());

        if(personalOptional.isPresent()){
            Personal personalExistente = personalOptional.get();
            if(personalExistente.getEstado()==Personal.ELIMINADO){
                personalExistente.recuperar();
                personalExistente.setNombre(model.getNombre());
                return PersonalMapper.toPersonalResponse(modelRepository.save(personalExistente));
            }else {
                throw new IllegalArgumentException("EL PERSONAL CON NOMBRE '"+newPersonalRequest.nombre()+"' YA EXISTE");
            }
        }

        return PersonalMapper.toPersonalResponse(modelRepository.save(model));
    }

    @Override
    public PersonalResponse actualizar(NewPersonalRequest newPersonalRequest, Integer id){
        Personal model = PersonalMapper.toEntity(newPersonalRequest);
        Optional<Personal> personalOptional = modelRepository.findById(id);
        if(personalOptional.isPresent()){
            if(personalOptional.get().getEstado()==Personal.ELIMINADO){
                throw new IllegalArgumentException("EL PERSONAL CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
            }
            Personal personal = personalOptional.get();
            personal.setNombre(model.getNombre());
            return PersonalMapper.toPersonalResponse(modelRepository.save(personal));
        }
            throw new IllegalArgumentException("EL PERSONAL CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public void eliminar(Personal model) {
        model.eliminar();
        modelRepository.save(model);        //con "save" realizamos tal tarea, el repository traduce la orden a SQL
    }

    @Override
    public void recuperar(Personal model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
