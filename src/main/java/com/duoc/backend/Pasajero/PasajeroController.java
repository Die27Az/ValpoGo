package com.duoc.backend.Pasajero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/pasajero")
public class PasajeroController {

    private final PasajeroModelAssembler assembler;
    private final PasajeroService pasajeroService;
    
    //@Autowired
    public PasajeroController(PasajeroModelAssembler assembler, PasajeroService pasajeroService) {
        this.assembler = assembler;
        this.pasajeroService = pasajeroService;
    }



    @GetMapping("/register")
    public String greetings(@RequestParam(defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }

    @GetMapping
    public CollectionModel<PasajeroModel> getAllPasajeros() {
        List<Pasajero> pasajeros = (List<Pasajero>) pasajeroService.getAllPasajeros();

        List<PasajeroModel> models = pasajeros.stream()
            .map(pasajero -> assembler.toModel(pasajero)) 
            .collect(Collectors.toList());

        return CollectionModel.of(models,
            linkTo(methodOn(PasajeroController.class).getAllPasajeros()).withSelfRel());
    }

    @GetMapping("/{id}")
    public PasajeroModel getPasajeroById(@PathVariable Long id) {
        Pasajero pasajero = pasajeroService.getPasajeroById(id);
        return assembler.toModel(pasajero);
    }

    @PostMapping
    public Pasajero savePasajero(@RequestBody Pasajero pasajero) {
        return pasajeroService.savePasajero(pasajero);
    }

    @DeleteMapping("/{id}")
    public void deletePasajero(@PathVariable Long id) {
        pasajeroService.deletePasajero(id);
    }
    
    @PutMapping("/{id}")
    public Pasajero updateBus(@PathVariable Long id, @RequestBody Pasajero pasajero) {
        pasajero.setId(id); // Aseguramos que el ID del cuerpo coincida con el de la URL
        return pasajeroService.updatePasajero(pasajero);
    }


}




