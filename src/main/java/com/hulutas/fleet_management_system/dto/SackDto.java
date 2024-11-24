package com.hulutas.fleet_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulutas.fleet_management_system.enums.SackStatus;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.model.Package;
import com.hulutas.fleet_management_system.model.Vehicle;

import java.util.List;

public record SackDto(
        Long id,
        SackStatus status,
        String barcode,
        @JsonIgnore
        DeliveryPointDto deliveryPoint,
        @JsonIgnore
        List<PackageDto> packages,
        @JsonIgnore
        VehicleDto vehicle) {
}
