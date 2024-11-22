package com.hulutas.fleet_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulutas.fleet_management_system.model.Package;
import com.hulutas.fleet_management_system.model.Sack;

import java.util.List;

public record VehicleDto(
        Long id,
        String plateNumber,
        @JsonIgnore
        List<Sack> sacks,
        @JsonIgnore
        List<Package> packages) {
}
