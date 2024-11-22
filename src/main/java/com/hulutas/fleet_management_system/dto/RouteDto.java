package com.hulutas.fleet_management_system.dto;

import java.util.List;

public record RouteDto(
        int deliveryPoint,
        List<VehicleLoadDto> deliveries
) {
}
