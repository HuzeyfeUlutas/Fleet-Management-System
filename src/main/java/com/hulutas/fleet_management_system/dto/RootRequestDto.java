package com.hulutas.fleet_management_system.dto;

import java.util.List;

public record RootRequestDto(
        String vehicle,
        List<RouteDto> route
) {
}
