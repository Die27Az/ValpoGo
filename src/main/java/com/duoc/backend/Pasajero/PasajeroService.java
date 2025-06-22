package com.duoc.backend.Pasajero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasajeroService {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    public Iterable<Pasajero> getAllPasajeros() {
        return pasajeroRepository.findAll();
    }

    public Pasajero getPasajeroById(Long id) {
        return pasajeroRepository.findById(id).orElse(null);
    }

    public Pasajero savePasajero(Pasajero pasajero) {
        return pasajeroRepository.save(pasajero);
    }

    public void deletePasajero(Long id) {
        pasajeroRepository.deleteById(id);
    }

    public Pasajero updatePasajero(Pasajero pasajero) {
        Pasajero pasajeroExistente = pasajeroRepository.findById(pasajero.getId()).orElse(null);
        if (pasajeroExistente == null) {
            return null; 
        }

        if(pasajero.getNombreCompleto()!= null)
            pasajeroExistente.setNombreCompleto(pasajero.getNombreCompleto());
        
        if(pasajero.getEdad()!= 0)
            pasajeroExistente.setEdad(pasajero.getEdad());
        
        if(pasajero.getGeom_inicio()!=null)
            pasajeroExistente.setGeom_inicio(pasajero.getGeom_inicio());
    
    
        if(pasajero.getGeom_viaje()!= null)
            pasajeroExistente.setGeom_viaje(pasajero.getGeom_viaje());

                
        return pasajeroRepository.save(pasajeroExistente);
    }








}