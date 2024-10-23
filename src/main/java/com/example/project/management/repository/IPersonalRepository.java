package com.example.project.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.management.model.Personal;

public interface IPersonalRepository extends JpaRepository<Personal, Integer> {
    List<Personal> findByEstado(int estado); 
    Optional<Personal> findByNombre (String nombre);
}
