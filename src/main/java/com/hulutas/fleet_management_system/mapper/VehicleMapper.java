package com.hulutas.fleet_management_system.mapper;

import com.hulutas.fleet_management_system.dto.VehicleDto;
import com.hulutas.fleet_management_system.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleDto toDto(Vehicle vehicle);
    Vehicle toEntity(VehicleDto vehicleDto);

}