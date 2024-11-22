package com.hulutas.fleet_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulutas.fleet_management_system.enums.PackageStatus;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.model.Sack;
import com.hulutas.fleet_management_system.model.Vehicle;

public record PackageDto(
        Long id,
        PackageStatus packageStatus,
        String barcode,
        int desi,
        @JsonIgnore
        DeliveryPoint deliveryPoint,
        @JsonIgnore
        Sack sack,
        @JsonIgnore
        Vehicle vehicle) {
}
