package com.duoc.backend.Ruta;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RutaModelAssembler implements RepresentationModelAssembler<Ruta, RutaModel> {

    @Override
    public RutaModel toModel(Ruta ruta) {
        RutaModel model = new RutaModel();

        model.setId(ruta.getId());
        model.setNombreRuta(ruta.getNombreRuta());
        model.setDescripcion(ruta.getDescripcion());
        model.setGeometria(ruta.getGeometria());
        model.setVelocidades(ruta.getVelocidades());

        model.add(linkTo(methodOn(RutaController.class).getRutaById(ruta.getId())).withSelfRel());
        model.add(linkTo(methodOn(RutaController.class).getAllRutas()).withRel("todos"));

        return model;
    }
}