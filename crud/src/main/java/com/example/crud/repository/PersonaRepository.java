package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.model.Persona;
@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long>{

}

    
