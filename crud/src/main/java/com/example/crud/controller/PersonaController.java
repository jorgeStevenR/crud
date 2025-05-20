package com.example.crud.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.model.Persona;
import com.example.crud.services.PersonaServices;

@RestController
@RequestMapping("/api/persona")

public class PersonaController {
    @Autowired // Spring inyecta automáticamente una instancia de PersonaServices,
    private PersonaServices personaServices;
    
    @PostMapping("/guardar")                                                                                                            // @PostMapping("/guardar") Define un endpoint HTTP POST en la ruta /guardar. , Esto quiere decir que cuando hagas un POST a http://localhost:puerto/api/persona/guardar, se ejecutará este método.
    public ResponseEntity<Persona> guardar(@RequestBody Persona persona){                                                              // El método devuelve un objeto ResponseEntity<Persona>, que es una forma de responder con estado HTTP y contenido personalizado. ,  @RequestBody Persona persona Spring convierte automáticamente el JSON del cuerpo de la petición en una instancia de la clase Persona.
        Persona personaTemportal = personaServices.createPersona(persona);                                                              //  Llama al método createPersona() del servicio y guarda el resultado (una Persona guardada en la base de datos) en la variable personaTemportal.
        try{                                                                    
            return ResponseEntity.created(new URI("/api/persona/guardar" + personaTemportal.getId())).body(personaTemportal);           //ResponseEntity.created(...) Devuelve un estado HTTP 201 Created, indicando que el recurso fue creado con éxito. , new URI("/api/persona/guardar" + personaTemportal.getId()) Crea una URI de referencia para el nuevo recurso creado, por ejemplo: /api/persona/guardar5 si el ID es 5. Esto es una buena práctica REST para indicar dónde se puede acceder al nuevo recurso. , .body(personaTemportal) El cuerpo de la respuesta será el objeto personaTemportal, ya guardado en la base de datos.
        }catch(Exception e){                                                                                                            //Si ocurre algún error al construir la URI o guardar la persona, se captura la excepción y se devuelve una respuesta con estado 400 Bad Request, sin cuerpo.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/ListaDePersonas") // Muestra la lista de Personas
    public ResponseEntity<List<Persona>> listarTodasLasPersonas(){
        return ResponseEntity.ok(personaServices.getAllPersonas());
    }

    @DeleteMapping("/eliminarPersona/{id}") // Elilima una persona
    public ResponseEntity<String> eliminarPersonas(@PathVariable Long id){
        Optional<Persona> personaOptional = personaServices.findById(id);
        if(personaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("la persona con id " + id + " no existe ");
        }

        personaServices.deletePersona(personaOptional.get());
            return ResponseEntity.ok("Usuario con id " + id + " se ha eliminado.");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Persona>> ListarPersonasPorId(@PathVariable("id") long id){
        return ResponseEntity.ok(personaServices.findById(id));
        
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada){
        Optional<Persona> personaOptional = personaServices.findById(id);

        if (personaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Persona personaExistente = personaOptional.get();

        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setCorreo(personaActualizada.getCorreo());

        Persona personaGuardada = personaServices.createPersona(personaExistente);
        return ResponseEntity.ok(personaGuardada);
    }

}
