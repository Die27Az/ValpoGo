package com.duoc.backend.Pasajero;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombreCompleto;
    private int edad;
    private String geom_inicio;
    private String geom_viaje;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getGeom_inicio() {
        return geom_inicio;
    }
    public void setGeom_inicio(String geom_inicio) {
        this.geom_inicio = geom_inicio;
    }
    public String getGeom_viaje() {
        return geom_viaje;
    }
    public void setGeom_viaje(String geom_viaje) {
        this.geom_viaje = geom_viaje;
    }
    
    
    
}
