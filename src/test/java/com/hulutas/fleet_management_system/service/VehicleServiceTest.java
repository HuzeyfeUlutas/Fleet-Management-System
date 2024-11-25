package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.VehicleDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.VehicleMapper;
import com.hulutas.fleet_management_system.model.Vehicle;
import com.hulutas.fleet_management_system.repository.VehicleRepository;
import com.hulutas.fleet_management_system.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleMapper vehicleMapper;
    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        vehicleDto = new VehicleDto(2L,"Test123",null,null);
        vehicle = new Vehicle();
        vehicle.setPlateNumber(vehicleDto.plateNumber());
    }

    @Test
    void testGetAllVehicle() {
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDto);

        List<VehicleDto> result = vehicleService.getAllVehicle();

        assertNotNull(result);

        assertEquals("Test123", result.get(0).plateNumber());
    }

    @Test
    void testGetAllVehicle_shouldThrowExceptionWhenVehicleIsNull() {
        when(vehicleRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.getAllVehicle();
        });

        assertEquals("Vehicles not found", exception.getMessage());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testCreateVehicle() {
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(vehicleMapper.toEntity(any(VehicleDto.class))).thenReturn(vehicle);
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDto);

        VehicleDto result = vehicleService.createVehicle(vehicleDto);

        assertNotNull(result);
        assertEquals("Test123", result.plateNumber());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
        verify(vehicleMapper, times(1)).toDto(any(Vehicle.class));
    }

    @Test
    void testGetVehicleById() {
        when(vehicleRepository.findById(2L)).thenReturn(Optional.ofNullable(vehicle));
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDto);

        VehicleDto result = vehicleService.getVehicleById(2L);

        assertNotNull(result);
        assertEquals("Test123", result.plateNumber());
        verify(vehicleRepository, times(1)).findById(anyLong());
        verify(vehicleMapper, times(1)).toDto(any(Vehicle.class));
    }

    @Test
    void testGetVehicleById_shouldThrowExceptionWhenVehicleNotFound() {
        when(vehicleRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.getVehicleById(2L);
        });

        assertEquals("Vehicle not found with id: 2", exception.getMessage());
        verify(vehicleRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateVehicle() {
        VehicleDto updatedVehicleDto = new VehicleDto(2L, "TestUp", null,null);
        when(vehicleRepository.findById(2L)).thenReturn(Optional.ofNullable(vehicle));
        when(vehicleMapper.toEntity(any(VehicleDto.class))).thenReturn(vehicle);
        vehicle.setPlateNumber("TestUp");
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(updatedVehicleDto);

        VehicleDto result = vehicleService.updateVehicle(updatedVehicleDto);

        assertEquals(updatedVehicleDto.plateNumber(), vehicle.getPlateNumber());
        assertEquals("TestUp", updatedVehicleDto.plateNumber());
    }

    @Test
    void testUpdateVehicle_shouldThrowExceptionWhenVehicleNotFound() {
        when(vehicleRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.updateVehicle(vehicleDto);
        });

        assertEquals("Vehicle not found with id:2", exception.getMessage());
        verify(vehicleRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteVehicle() {
        Long vehicleId = 1L;
        when(vehicleRepository.existsById(vehicleId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.deleteVehicle(vehicleId);
        });

        assertEquals("Vehicle not found with id: " + vehicleId, exception.getMessage());
        verify(vehicleRepository, never()).deleteById(vehicleId);
    }
}
