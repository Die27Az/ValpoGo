package com.duoc.backend.Bus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

@Component
public class BusModelAssembler implements RepresentationModelAssembler<Bus, BusModel> {

    @Override
    @NonNull
    public BusModel toModel(Bus bus) {
        BusModel model = new BusModel();

        model.setId(bus.getId());
        model.setPatente(bus.getPatente());
        model.setConductor(bus.getConductor());
        model.setGeometria(bus.getGeometria());
        model.setCapacidad(bus.getCapacidad());
        model.setEstado(bus.getEstado());

        model.add(linkTo(methodOn(BusController.class).getBusById(bus.getId())).withSelfRel());
        model.add(linkTo(methodOn(BusController.class).getAllBuses()).withRel("todos"));

        return model;
    }
}