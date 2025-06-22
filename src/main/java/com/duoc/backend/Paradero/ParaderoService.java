package com.duoc.backend.Paradero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParaderoService {

    @Autowired
    private ParaderoRepository paraderoRepository;

    public Iterable<Paradero> getAllParaderos() {
        return paraderoRepository.findAll();
    }

    public Paradero getParaderoById(Long id) {
        return paraderoRepository.findById(id).orElse(null);
    }

    public Paradero saveParadero(Paradero paradero) {
        return paraderoRepository.save(paradero);
    }

    public void deleteParadero(Long id) {
        paraderoRepository.deleteById(id);
    }

    public Paradero updateParadero(Paradero paradero) {
        Paradero paraderoExistente = paraderoRepository.findById(paradero.getId()).orElse(null);
        if (paraderoExistente == null) {
            return null; 
        }
        if(paradero.getDireccion()!= null)
            paraderoExistente.setDireccion(paradero.getDireccion());

        if(paradero.getNombre()!=null)
            paraderoExistente.setNombre(paradero.getNombre());

        if(paradero.getGeometria()!=null)
            paraderoExistente.setGeometria(paradero.getGeometria());

        return paraderoRepository.save(paraderoExistente);
    }

}