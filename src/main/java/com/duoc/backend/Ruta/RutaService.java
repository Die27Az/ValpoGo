package com.duoc.backend.Ruta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    public Iterable<Ruta> getAllRutas() {
        return rutaRepository.findAll();
    }

    public Ruta getRutaById(Long id) {
        return rutaRepository.findById(id).orElse(null);
    }

    public Ruta saveRuta(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    public void deleteRuta(Long id) {
        rutaRepository.deleteById(id);
    }

    public Ruta updateRuta(Ruta ruta) {
        Ruta rutaExistente = rutaRepository.findById(ruta.getId()).orElse(null);
        if (rutaExistente == null) {
            return null; 
        }

        if (ruta.getNombreRuta() != null)
            rutaExistente.setNombreRuta(ruta.getNombreRuta());

        if (ruta.getDescripcion() != null)
            rutaExistente.setDescripcion(ruta.getDescripcion());

        if (ruta.getGeometria() != null)
            rutaExistente.setGeometria(ruta.getGeometria());

        if (ruta.getVelocidades() != null)
            rutaExistente.setVelocidades(ruta.getVelocidades());

        return rutaRepository.save(rutaExistente);
    }








}