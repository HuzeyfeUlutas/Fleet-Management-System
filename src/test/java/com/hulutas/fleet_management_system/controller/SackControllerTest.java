package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.enums.SackStatus;
import com.hulutas.fleet_management_system.service.SackService;
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

@WebMvcTest(SackController.class)
public class SackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SackService sackService;

    @InjectMocks
    private SackController sackController;

    private SackDto sackDto;

    @BeforeEach
    void setUp() {
        sackDto = new SackDto(3L, SackStatus.LOADED, "Test Sack", null, null, null);
    }

    @Test
    void testGetAllSacks() throws Exception {
        List<SackDto> sacks = Arrays.asList(sackDto);
        when(sackService.getAllSack()).thenReturn(sacks);

        mockMvc.perform(get("/sack"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].barcode").value("Test Sack"));
    }

    @Test
    void testCreateSack() throws Exception {
        when(sackService.createSack(any(SackDto.class))).thenReturn(sackDto);

        mockMvc.perform(post("/sack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\": \"New Sack\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Sack"));
    }

    @Test
    void testGetSackById() throws Exception {
        when(sackService.getSackId(1L)).thenReturn(sackDto);

        mockMvc.perform(get("/sack/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Sack"));
    }

    @Test
    void testUpdateSack() throws Exception {
        when(sackService.updateSack(any(SackDto.class))).thenReturn(sackDto);

        mockMvc.perform(put("/sack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\": \"Updated Sack\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Sack"));
    }

    @Test
    void testDeleteSack() throws Exception {
        doNothing().when(sackService).deleteSack(1L);

        mockMvc.perform(delete("/sack/1"))
                .andExpect(status().isNoContent());

        verify(sackService, times(1)).deleteSack(1L);
    }
}