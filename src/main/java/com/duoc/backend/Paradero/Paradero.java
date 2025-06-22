package com.duoc.backend.Paradero;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Paradero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String direccion;
    private String nombre;
    private String geometria;

    // getters y setters
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getGeometria() {
        return geometria;
    }
    public void setGeometria(String geometria) {
        this.geometria = geometria;
    }

    

}
