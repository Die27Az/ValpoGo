package com.duoc.backend.Bus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BusTest {

    @Test
    public void testBusGettersAndSetters() {
        Bus bus = new Bus();
        bus.setId(1L);
        bus.setPatente("ABCD12");
        bus.setConductor("Juan Pérez");
        bus.setGeometria("LINESTRING(1 1, 2 2)");
        bus.setCapacidad(45);
        bus.setEstado("EN_SERVICIO");

        assertEquals(1L, bus.getId());
        assertEquals("ABCD12", bus.getPatente());
        assertEquals("Juan Pérez", bus.getConductor());
        assertEquals("LINESTRING(1 1, 2 2)", bus.getGeometria());
        assertEquals(45, bus.getCapacidad());
        assertEquals("EN_SERVICIO", bus.getEstado());
    }
}

