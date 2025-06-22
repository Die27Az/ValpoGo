package com.duoc.backend.Ruta;

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
@RequestMapping("/ruta")
public class RutaController {

    private final RutaModelAssembler assembler;
    private final RutaService rutaService;

    //@Autowired
    public RutaController(RutaModelAssembler assembler, RutaService rutaService) {
        this.assembler = assembler;
        this.rutaService = rutaService;
    }



    @GetMapping("/register")
    public String greetings(@RequestParam(defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }

    @GetMapping
    public CollectionModel<RutaModel> getAllRutas() {
        List<Ruta> rutas = (List<Ruta>) rutaService.getAllRutas();

        List<RutaModel> models = rutas.stream()
            .map(ruta -> assembler.toModel(ruta))
            .collect(Collectors.toList());

        return CollectionModel.of(models,
            linkTo(methodOn(RutaController.class).getAllRutas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public RutaModel getRutaById(@PathVariable Long id) {
        Ruta ruta = rutaService.getRutaById(id);
        return assembler.toModel(ruta);
    }

    @PostMapping
    public Ruta saveRuta(@RequestBody Ruta ruta) {
        return rutaService.saveRuta(ruta);
    }

    @DeleteMapping("/{id}")
    public void deleteRuta(@PathVariable Long id) {
        rutaService.deleteRuta(id);
    }
    
    @PutMapping("/{id}")
    public Ruta updateRuta(@PathVariable Long id, @RequestBody Ruta ruta) {
        ruta.setId(id); // Aseguramos que el ID del cuerpo coincida con el de la URL
        return rutaService.updateRuta(ruta);
    }


}




