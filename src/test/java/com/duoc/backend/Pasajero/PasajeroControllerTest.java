package com.duoc.backend.Pasajero;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;

public class PasajeroControllerTest {

    @Mock
    private PasajeroModelAssembler assembler;

    @Mock
    private PasajeroService pasajeroService;

    @InjectMocks
    private PasajeroController pasajeroController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGreetings() {
        String result = pasajeroController.greetings("Diego");
        assertEquals("Hello {Diego}", result);
    }

    @Test
    public void testGetAllPasajeros() {
        Pasajero pasajero = new Pasajero();
        pasajero.setId(1L);

        when(pasajeroService.getAllPasajeros()).thenReturn(List.of(pasajero));
        when(assembler.toModel(pasajero)).thenReturn(new MockPasajeroModel(pasajero));

        CollectionModel<PasajeroModel> result = pasajeroController.getAllPasajeros();

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testGetPasajeroById() {
        Pasajero pasajero = new Pasajero();
        pasajero.setId(1L);

        when(pasajeroService.getPasajeroById(1L)).thenReturn(pasajero);
        when(assembler.toModel(pasajero)).thenReturn(new MockPasajeroModel(pasajero));

        PasajeroModel result = pasajeroController.getPasajeroById(1L);

        assertNotNull(result);
        assertEquals(1L, ((MockPasajeroModel) result).getPasajero().getId());
    }

    @Test
    public void testSavePasajero() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombreCompleto("Juan");

        when(pasajeroService.savePasajero(pasajero)).thenReturn(pasajero);

        Pasajero result = pasajeroController.savePasajero(pasajero);

        assertEquals("Juan", result.getNombreCompleto());
    }

    @Test
    public void testDeletePasajero() {
        pasajeroController.deletePasajero(1L);
        verify(pasajeroService, times(1)).deletePasajero(1L);
    }

    @Test
    public void testUpdatePasajero() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombreCompleto("Pedro");

        when(pasajeroService.updatePasajero(any(Pasajero.class))).thenReturn(pasajero);

        Pasajero updated = pasajeroController.updateBus(1L, pasajero);

        assertEquals("Pedro", updated.getNombreCompleto());
        assertEquals(1L, pasajero.getId()); // Verifica que setId fue llamado
    }

    // Clase mock que expone el objeto pasajero
    static class MockPasajeroModel extends PasajeroModel {
        private final Pasajero pasajero;

        public MockPasajeroModel(Pasajero pasajero) {
            this.pasajero = pasajero;
        }

        public Pasajero getPasajero() {
            return pasajero;
        }
    }
}
