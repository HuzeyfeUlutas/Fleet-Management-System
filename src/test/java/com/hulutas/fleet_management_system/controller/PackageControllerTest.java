package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.enums.PackageStatus;
import com.hulutas.fleet_management_system.service.PackageService;
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

@WebMvcTest(PackageController.class)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageService packageService;

    @InjectMocks
    private PackageController packageController;

    private PackageDto packageDto;

    @BeforeEach
    void setUp() {
        packageDto = new PackageDto(3L, PackageStatus.CREATED,"Test Package",1,null,null,null);
    }

    @Test
    void testGetAllPackages() throws Exception {
        List<PackageDto> packages = Arrays.asList(packageDto);
        when(packageService.getAllPackage()).thenReturn(packages);

        mockMvc.perform(get("/package"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].barcode").value("Test Package"));
    }

    @Test
    void testCreatePackage() throws Exception {
        when(packageService.createPackage(any(PackageDto.class))).thenReturn(packageDto);

        mockMvc.perform(post("/package")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\": \"New Package\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Package"));
    }

    @Test
    void testGetPackageById() throws Exception {
        when(packageService.getPackageById(1L)).thenReturn(packageDto);

        mockMvc.perform(get("/package/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Package"));
    }

    @Test
    void testUpdatePackage() throws Exception {
        when(packageService.updatePackage(any(PackageDto.class))).thenReturn(packageDto);

        mockMvc.perform(put("/package")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"barcode\": \"Updated Package\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value("Test Package"));
    }

    @Test
    void testDeletePackage() throws Exception {
        doNothing().when(packageService).deletePackage(1L);

        mockMvc.perform(delete("/package/1"))
                .andExpect(status().isNoContent());

        verify(packageService, times(1)).deletePackage(1L);
    }
}