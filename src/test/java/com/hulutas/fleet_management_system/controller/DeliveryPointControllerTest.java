package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.service.DeliveryPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliveryPointController.class)
public class DeliveryPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryPointService deliveryPointService;

    @InjectMocks
    private DeliveryPointController deliveryPointController;

    private DeliveryPointDto deliveryPointDto;

    @BeforeEach
    void setUp() {
        deliveryPointDto = new DeliveryPointDto(1L, "Test Delivery Point", null,null);
    }

    @Test
    void testGetAllDeliveryPoints() throws Exception {
        List<DeliveryPointDto> deliveryPoints = Arrays.asList(deliveryPointDto);
        when(deliveryPointService.getAllDeliveryPoints()).thenReturn(deliveryPoints);

        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Delivery Point"));
    }

    @Test
    void testCreateDeliveryPoint() throws Exception {
        when(deliveryPointService.createDeliveryPoint(any(DeliveryPointDto.class))).thenReturn(deliveryPointDto);

        mockMvc.perform(post("/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Delivery Point\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Delivery Point"));
    }

    @Test
    void testGetDeliveryPointById() throws Exception {
        when(deliveryPointService.getDeliveryPointById(1L)).thenReturn(deliveryPointDto);

        mockMvc.perform(get("/delivery/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Delivery Point"));
    }

    @Test
    void testUpdateDeliveryPoint() throws Exception {
        when(deliveryPointService.updateDeliveryPoint(any(DeliveryPointDto.class))).thenReturn(deliveryPointDto);

        mockMvc.perform(put("/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Delivery Point\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Delivery Point"));
    }

    @Test
    void testDeleteDeliveryPoint() throws Exception {
        doNothing().when(deliveryPointService).deleteDeliveryPoint(1L);

        mockMvc.perform(delete("/delivery/1"))
                .andExpect(status().isNoContent());

        verify(deliveryPointService, times(1)).deleteDeliveryPoint(1L);
    }
}