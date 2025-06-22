package com.duoc.backend.Paradero;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paradero")
public class ParaderoController {

    private final ParaderoModelAssembler assembler;
    private final ParaderoService paraderoService;

    //@Autowired
    public ParaderoController(ParaderoModelAssembler assembler, ParaderoService paraderoService) {
        this.assembler = assembler;
        this.paraderoService = paraderoService;
    }



    @GetMapping("/register")
    public String greetings(@RequestParam(defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }

    @GetMapping
    public CollectionModel<ParaderoModel> getAllParaderos() {
        List<Paradero> paraderos = (List<Paradero>) paraderoService.getAllParaderos();

        List<ParaderoModel> models = paraderos.stream()
            .map(paradero -> assembler.toModel(paradero))
            .collect(Collectors.toList());

        return CollectionModel.of(models,
            linkTo(methodOn(ParaderoController.class).getAllParaderos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ParaderoModel getParaderoById(@PathVariable Long id) {
        Paradero paradero = paraderoService.getParaderoById(id);
        return assembler.toModel(paradero);
    }

    @PostMapping
    public Paradero saveParadero(@RequestBody Paradero paradero) {
        return paraderoService.saveParadero(paradero);
    }

    @DeleteMapping("/{id}")
    public void deleteParadero(@PathVariable Long id) {
        paraderoService.deleteParadero(id);
    }
    
    @PutMapping("/{id}")
    public Paradero updateParadero(@PathVariable Long id, @RequestBody Paradero paradero) {
        paradero.setId(id);
        return paraderoService.updateParadero(paradero);
    }


}




