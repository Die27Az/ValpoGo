package com.duoc.backend.Ruta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RutaTest {

    @Test
    public void testGettersAndSetters() {
        Ruta ruta = new Ruta();

        ruta.setId(5L);
        ruta.setNombreRuta("Ruta 42");
        ruta.setDescripcion("Desde centro a terminal");
        ruta.setGeometria("LINESTRING(...)");
        ruta.setVelocidades("30, 45, 50");

        assertEquals(5L, ruta.getId());
        assertEquals("Ruta 42", ruta.getNombreRuta());
        assertEquals("Desde centro a terminal", ruta.getDescripcion());
        assertEquals("LINESTRING(...)", ruta.getGeometria());
        assertEquals("30, 45, 50", ruta.getVelocidades());
    }
}
