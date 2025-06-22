package com.duoc.backend.Bus;

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
@RequestMapping("/bus")
public class BusController {

    private final BusModelAssembler assembler;
    private final BusService busService;
    
    //@Autowired
    public BusController(BusModelAssembler assembler, BusService busService) {
        this.assembler = assembler;
        this.busService = busService;
    }



    @GetMapping("/register")
    public String greetings(@RequestParam(defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }

    @GetMapping
    public CollectionModel<BusModel> getAllBuses() {
        List<Bus> buses = (List<Bus>) busService.getAllBuses();

        List<BusModel> models = buses.stream()
            .map(bus -> assembler.toModel(bus)) 
            .collect(Collectors.toList());

        return CollectionModel.of(models,
            linkTo(methodOn(BusController.class).getAllBuses()).withSelfRel());
    }

    @GetMapping("/{id}")
    public BusModel getBusById(@PathVariable Long id) {
        Bus bus = busService.getBusById(id);
        return assembler.toModel(bus);
    }

    @PostMapping
    public Bus saveBus(@RequestBody Bus bus) {
        return busService.saveBus(bus);
    }

    @DeleteMapping("/{id}")
    public void deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
    }
    
    @PutMapping("/{id}")
    public Bus updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        bus.setId(id); // Aseguramos que el ID del cuerpo coincida con el de la URL
        return busService.updateBus(bus);
    }


}




