package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.RootRequestDto;
import com.hulutas.fleet_management_system.dto.VehicleDto;
import com.hulutas.fleet_management_system.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private VehicleDto vehicleDto;
    private RootRequestDto rootRequestDto;

    @BeforeEach
    void setUp() {
        vehicleDto = new VehicleDto(3L, "Test Vehicle", null, null);

        rootRequestDto = new RootRequestDto("Test Vehicle", null);
    }

    @Test
    void testGetAllVehicles() throws Exception {
        List<VehicleDto> vehicles = Arrays.asList(vehicleDto);
        when(vehicleService.getAllVehicle()).thenReturn(vehicles);

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].plateNumber").value("Test Vehicle"));
    }

    @Test
    void testCreateVehicle() throws Exception {
        when(vehicleService.createVehicle(any(VehicleDto.class))).thenReturn(vehicleDto);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\": \"New Vehicle\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plateNumber").value("Test Vehicle"));
    }

    @Test
    void testGetVehicleById() throws Exception {
        when(vehicleService.getVehicleById(1L)).thenReturn(vehicleDto);

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plateNumber").value("Test Vehicle"));
    }

    @Test
    void testUpdateVehicle() throws Exception {
        when(vehicleService.updateVehicle(any(VehicleDto.class))).thenReturn(vehicleDto);

        mockMvc.perform(put("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\": \"Updated Vehicle\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plateNumber").value("Test Vehicle"));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(1L);

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicle(1L);
    }

    @Test
    void testDistributePackages() throws Exception {
        when(vehicleService.distributePackages(eq("1"), any(RootRequestDto.class))).thenReturn(rootRequestDto);

        mockMvc.perform(post("/vehicles/1/distribute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicle\": \"Test Vehicle\"}"))
                .andExpect(status().isOk());
    }
}