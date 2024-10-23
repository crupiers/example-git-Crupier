package com.example.project.management.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.project.management.controller.request.NewPersonalRequest;
import com.example.project.management.controller.response.PersonalResponse;
import com.example.project.management.mapper.PersonalMapper;
import com.example.project.management.model.Personal;
import com.example.project.management.service.IPersonalService;

import java.util.List;

@RestController 
@RequestMapping("eCommerce") //mapeo para que sea accesible
@CrossOrigin(value=" http://localhost:8080") //hago accesible el endpoint por el puerto 8080

//esta clase no va a necesitar un metodo de actualizacion ya que solo se guarda el nombre de un personal
public class PersonalController {

    //genero el logger, que me permite informar al usuario
    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

    @Autowired //autowired me sirve que las dependencias se injecten solas
    private IPersonalService modelService; //el service se encarga de realizar las tareas

    @GetMapping({"/personal"})
    public List<PersonalResponse> getAll(){
        logger.info("ESTOS SON TODOS LOS COLORES DISPONIBLES");

        return modelService.listar();
    }

    @GetMapping("/personal/{id}") //busco el personal por su nombre
    public ResponseEntity<PersonalResponse> buscarPorId(@PathVariable Integer id){
        //usamos el servicio para encontrar un personal según su nombre
        Personal model = modelService.buscarPorId(id);
        if(model==null || model.getEstado() == Personal.ELIMINADO){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL COLOR: "+id);
        }
        PersonalResponse personalResponse = PersonalMapper.toPersonalResponse(model);

        return ResponseEntity.ok(personalResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    
    @PostMapping("/personal") //uso de la instruccion "POST"
    public PersonalResponse crear(@RequestBody @Valid NewPersonalRequest newPersonalRequest){
        return modelService.crear(newPersonalRequest);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/personal/actualizar/{id}")
    public PersonalResponse actualizar(@RequestBody @Valid NewPersonalRequest newPersonalRequest, @PathVariable Integer id){
        return modelService.actualizar(newPersonalRequest, id);
    }

    @PutMapping("/personal/recuperar/{id}") //mapeamos el "PUT" para volver a recuperar un personal eliminado

    public ResponseEntity<Void> recuperar(@PathVariable Integer id){
        Personal model = modelService.buscarPorId(id);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL COLOR '"+id+ "' NUNCA FUE REGISTRADO NI BORRADO ANTERIORMENTE");
        }
        //si encuentro el personal que quiero recuperar
        modelService.recuperar(model); //llamo al servicio para que se encargue de recuperarlo

        return ResponseEntity.ok().build(); 
    }

    @DeleteMapping("/personal/{id}") //mapeo el "DELETE" del postman para que realice esta funcion de eliminado logico
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        Personal model = modelService.buscarPorId(id);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL COLOR '"+id+"' NO EXISTE Y NO PUEDE SER BORRADO");
        }
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/personal/existe/{nombre}")
    public Personal buscarPorNombre(@PathVariable String nombre){
        Personal model = modelService.buscarPorNombre(nombre);
        return model;
    }

}
