package com.duoc.backend.Pasajero;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PasajeroTest {

    @Test
    public void testGettersAndSetters() {
        Pasajero pasajero = new Pasajero();

        pasajero.setId(10L);
        pasajero.setNombreCompleto("Ana Torres");
        pasajero.setEdad(30);
        pasajero.setGeom_inicio("POINT(-70.6 -33.5)");
        pasajero.setGeom_viaje("LINESTRING(...)");

        assertEquals(10L, pasajero.getId());
        assertEquals("Ana Torres", pasajero.getNombreCompleto());
        assertEquals(30, pasajero.getEdad());
        assertEquals("POINT(-70.6 -33.5)", pasajero.getGeom_inicio());
        assertEquals("LINESTRING(...)", pasajero.getGeom_viaje());
    }
}
