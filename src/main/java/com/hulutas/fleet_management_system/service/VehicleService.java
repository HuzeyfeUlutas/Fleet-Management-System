package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.RootRequestDto;
import com.hulutas.fleet_management_system.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    List<VehicleDto> getAllVehicle();
    VehicleDto createVehicle(VehicleDto vehicleDto);
    VehicleDto getVehicleById(Long id);
    VehicleDto updateVehicle(VehicleDto vehicleDto);
    void deleteVehicle(Long id);
    RootRequestDto distributePackages(String vehicleId, RootRequestDto rootRequestDto);
}
