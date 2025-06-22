package com.duoc.backend.Paradero;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ParaderoModelAssembler implements RepresentationModelAssembler<Paradero, ParaderoModel> {

    @Override
    public ParaderoModel toModel(Paradero paradero) {
        ParaderoModel model = new ParaderoModel();

        model.setId(paradero.getId());
        model.setDireccion(paradero.getDireccion());
        model.setNombre(paradero.getNombre());
        model.setGeometria(paradero.getGeometria());

        // Agregar enlaces HATEOAS
        // Aquí se agregan los enlaces para la representación del modelo
        model.add(linkTo(methodOn(ParaderoController.class).getParaderoById(paradero.getId())).withSelfRel());
        model.add(linkTo(methodOn(ParaderoController.class).getAllParaderos()).withRel("todos"));

        return model;
    }
}