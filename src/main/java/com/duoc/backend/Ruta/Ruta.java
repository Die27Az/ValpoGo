package com.duoc.backend.Ruta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombreRuta;
    private String descripcion;
    private String geometria;
    private String velocidades;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreRuta() {
        return nombreRuta;
    }
    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getGeometria() {
        return geometria;
    }
    public void setGeometria(String geometria) {
        this.geometria = geometria;
    }
    public String getVelocidades() {
        return velocidades;
    }
    public void setVelocidades(String velocidades) {
        this.velocidades = velocidades;
    }

    // getters and setters
    
    
    
}
