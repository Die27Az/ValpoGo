package com.duoc.backend.Pasajero;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PasajeroModelAssembler implements RepresentationModelAssembler<Pasajero, PasajeroModel> {

    @Override
    public PasajeroModel toModel(Pasajero pasajero) {
        PasajeroModel model = new PasajeroModel();

        model.setId(pasajero.getId());
        model.setNombreCompleto(pasajero.getNombreCompleto());
        model.setEdad(pasajero.getEdad());
        model.setGeom_inicio(pasajero.getGeom_inicio());
        model.setGeom_viaje(pasajero.getGeom_viaje());
       
        model.add(linkTo(methodOn(PasajeroController.class).getPasajeroById(pasajero.getId())).withSelfRel());
        model.add(linkTo(methodOn(PasajeroController.class).getAllPasajeros()).withRel("todos"));

        return model;
    }
}