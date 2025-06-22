package com.duoc.backend.Paradero;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.hateoas.CollectionModel;

public class ParaderoControllerTest {

    private ParaderoService paraderoService;
    private ParaderoModelAssembler assembler;
    private ParaderoController paraderoController;

    @BeforeEach
    public void setup() {
        paraderoService = mock(ParaderoService.class);
        assembler = mock(ParaderoModelAssembler.class);
        paraderoController = new ParaderoController(assembler, paraderoService);
    }

    @Test
    public void testGreetings() {
        String result = paraderoController.greetings("Juan");
        assertEquals("Hello {Juan}", result);
    }

    @Test
    public void testGetAllParaderos() {
        Paradero paradero1 = new Paradero();
        paradero1.setId(1L);
        Paradero paradero2 = new Paradero();
        paradero2.setId(2L);

        List<Paradero> paraderos = Arrays.asList(paradero1, paradero2);

        when(paraderoService.getAllParaderos()).thenReturn(paraderos);
        when(assembler.toModel(paradero1)).thenReturn(new MockParaderoModel(paradero1));
        when(assembler.toModel(paradero2)).thenReturn(new MockParaderoModel(paradero2));

        CollectionModel<ParaderoModel> result = paraderoController.getAllParaderos();

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

@Test
public void testGetParaderoById() {
    Paradero paradero = new Paradero();
    paradero.setId(1L);

    when(paraderoService.getParaderoById(1L)).thenReturn(paradero);
    when(assembler.toModel(paradero)).thenReturn(new MockParaderoModel(paradero));

    ParaderoModel result = paraderoController.getParaderoById(1L);

    // Convertimos el mock para poder hacer la verificación
    assertNotNull(result);
    assertEquals(1L, ((MockParaderoModel) result).getParadero().getId());
}

    @Test
    public void testSaveParadero() {
        Paradero paradero = new Paradero();
        when(paraderoService.saveParadero(paradero)).thenReturn(paradero);

        Paradero result = paraderoController.saveParadero(paradero);
        assertEquals(paradero, result);
    }

    @Test
    public void testDeleteParadero() {
        doNothing().when(paraderoService).deleteParadero(1L);
        paraderoController.deleteParadero(1L);
        verify(paraderoService, times(1)).deleteParadero(1L);
    }

    @Test
    public void testUpdateParadero() {
        Paradero paradero = new Paradero();
        paradero.setId(1L);

        when(paraderoService.updateParadero(paradero)).thenReturn(paradero);

        Paradero result = paraderoController.updateParadero(1L, paradero);
        assertEquals(1L, result.getId());
    }

    // Mock ParaderoModel para pruebas
    static class MockParaderoModel extends ParaderoModel {
        private final Paradero paradero;

        public MockParaderoModel(Paradero paradero) {
            this.paradero = paradero;
        }

        public Paradero getParadero() {
            return paradero;
        }
    }
}
