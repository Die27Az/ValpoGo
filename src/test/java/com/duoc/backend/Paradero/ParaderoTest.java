package com.duoc.backend.Paradero;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ParaderoTest {

    @Test
    public void testGettersAndSetters() {
        Paradero paradero = new Paradero();

        paradero.setId(1L);
        paradero.setDireccion("Av. Central 123");
        paradero.setNombre("Paradero 1");
        paradero.setGeometria("POINT(-71.5 -33.0)");

        assertEquals(1L, paradero.getId());
        assertEquals("Av. Central 123", paradero.getDireccion());
        assertEquals("Paradero 1", paradero.getNombre());
        assertEquals("POINT(-71.5 -33.0)", paradero.getGeometria());
    }
}

