package com.example.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.model.Persona;
import com.example.crud.repository.PersonaRepository;

@Service
public class PersonaServices {

    @Autowired // Esta anotacione es para decir al servicio que esta conecatado con el repositorio y esta haciendo uso de la base de datos y los objetos
    private PersonaRepository personaRepository;

    public Persona createPersona(Persona persona){
       return personaRepository.save(persona); // se invoca a la clase PersonaRepository y le vamos a decir con el metodo save que guarde una persona
    }

    public List<Persona> getAllPersonas(){
        return personaRepository.findAll(); //Llama al método findAll() del repositorio personaRepository (que extiende de JpaRepository o CrudRepository) y devuelve una lista con todos los registros de la tabla persona en la base de datos.
    }

    public void  delete(Persona persona){
         personaRepository.delete(persona); //Este método elimina de la base de datos la entidad Persona que se pasa como parámetro, usando el método delete() del personaRepository.
    }

    public Optional<Persona> findById(Long id){
        return personaRepository.findById(id);
    }

    
    
}
