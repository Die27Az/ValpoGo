package com.duoc.backend.Ruta;

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

public class RutaControllerTest {

    @Mock
    private RutaModelAssembler assembler;

    @Mock
    private RutaService rutaService;

    @InjectMocks
    private RutaController rutaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGreetings() {
        String result = rutaController.greetings("Diego");
        assertEquals("Hello {Diego}", result);
    }

    @Test
    public void testGetAllRutas() {
        Ruta ruta = new Ruta();
        ruta.setId(1L);

        when(rutaService.getAllRutas()).thenReturn(List.of(ruta));
        when(assembler.toModel(ruta)).thenReturn(new MockRutaModel(ruta));

        CollectionModel<RutaModel> result = rutaController.getAllRutas();

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testGetRutaById() {
        Ruta ruta = new Ruta();
        ruta.setId(1L);

        when(rutaService.getRutaById(1L)).thenReturn(ruta);
        when(assembler.toModel(ruta)).thenReturn(new MockRutaModel(ruta));

        RutaModel result = rutaController.getRutaById(1L);

        assertNotNull(result);
        assertEquals(1L, ((MockRutaModel) result).getRuta().getId());
    }

    @Test
    public void testSaveRuta() {
        Ruta ruta = new Ruta();
        ruta.setNombreRuta("Ruta A");

        when(rutaService.saveRuta(ruta)).thenReturn(ruta);

        Ruta result = rutaController.saveRuta(ruta);

        assertEquals("Ruta A", result.getNombreRuta());
    }

    @Test
    public void testDeleteRuta() {
        rutaController.deleteRuta(1L);
        verify(rutaService, times(1)).deleteRuta(1L);
    }

    @Test
    public void testUpdateRuta() {
        Ruta ruta = new Ruta();
        ruta.setDescripcion("Modificada");

        when(rutaService.updateRuta(any(Ruta.class))).thenReturn(ruta);

        Ruta updated = rutaController.updateRuta(1L, ruta);

        assertEquals("Modificada", updated.getDescripcion());
        assertEquals(1L, ruta.getId());
    }

    // Clase mock que permite exponer el objeto Ruta
    static class MockRutaModel extends RutaModel {
        private final Ruta ruta;

        public MockRutaModel(Ruta ruta) {
            this.ruta = ruta;
        }

        public Ruta getRuta() {
            return ruta;
        }
    }
}
