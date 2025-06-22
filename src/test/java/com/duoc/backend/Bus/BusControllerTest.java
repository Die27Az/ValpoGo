package com.duoc.backend.Bus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;

public class BusControllerTest {

    @Mock
    private BusService busService;

    @Mock
    private BusModelAssembler assembler;

    @InjectMocks
    private BusController busController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGreetings_DefaultName() {
        String response = busController.greetings("Juan");
        assertEquals("Hello {Juan}", response);
    }

    @Test
    public void testGetAllBuses() {
        Bus bus1 = new Bus();
        bus1.setId(1L);
        Bus bus2 = new Bus();
        bus2.setId(2L);

        List<Bus> busList = Arrays.asList(bus1, bus2);
        when(busService.getAllBuses()).thenReturn(busList);
        BusModel mockModel1 = mock(BusModel.class);
        BusModel mockModel2 = mock(BusModel.class);

        when(assembler.toModel(bus1)).thenReturn(mockModel1);
        when(assembler.toModel(bus2)).thenReturn(mockModel2);

        CollectionModel<BusModel> result = busController.getAllBuses();

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(busService, times(1)).getAllBuses();
    }

    @Test
    public void testGetBusById() {
    Bus bus = new Bus();
    bus.setId(1L);

    BusModel mockModel = mock(BusModel.class);

    when(busService.getBusById(1L)).thenReturn(bus);
    when(assembler.toModel(bus)).thenReturn(mockModel);

    BusModel result = busController.getBusById(1L);

    assertNotNull(result);

}

    @Test
    public void testSaveBus() {
        Bus bus = new Bus();
        bus.setId(1L);
        when(busService.saveBus(bus)).thenReturn(bus);

        Bus result = busController.saveBus(bus);

        assertEquals(bus, result);
        verify(busService, times(1)).saveBus(bus);
    }

    @Test
    public void testDeleteBus() {
        Long busId = 1L;
        doNothing().when(busService).deleteBus(busId);

        busController.deleteBus(busId);

        verify(busService, times(1)).deleteBus(busId);
    }

    @Test
    public void testUpdateBus() {
        Bus bus = new Bus();
        bus.setId(1L);
        when(busService.updateBus(bus)).thenReturn(bus);

        Bus result = busController.updateBus(1L, bus);

        assertEquals(bus, result);
        verify(busService).updateBus(bus);
    }
}

