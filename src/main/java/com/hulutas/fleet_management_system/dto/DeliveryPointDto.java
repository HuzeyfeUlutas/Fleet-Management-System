package com.hulutas.fleet_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record DeliveryPointDto(
        Long id,
        String name,
        @JsonIgnore
        List<SackDto> sacks,
        @JsonIgnore
        List<PackageDto> packages) {
}
