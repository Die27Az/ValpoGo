package com.duoc.backend.Bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Iterable<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    public Bus updateBus(Bus bus) {
        Bus busExistente = busRepository.findById(bus.getId()).orElse(null);
        if (busExistente == null) {
            return null; 
        }
        if(bus.getPatente()!= null)
            busExistente.setPatente(bus.getPatente());
        
        if(bus.getConductor()!=null)
            busExistente.setConductor(bus.getConductor());
        
        if(bus.getGeometria()!=null)
            busExistente.setGeometria(bus.getGeometria());
    
    
        if(bus.getCapacidad()!= 0)
            busExistente.setCapacidad(bus.getCapacidad());

        if(bus.getEstado()!=null)
            busExistente.setEstado(bus.getEstado());

        
        return busRepository.save(busExistente);
    }








}