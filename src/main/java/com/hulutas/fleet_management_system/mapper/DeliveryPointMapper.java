package com.hulutas.fleet_management_system.mapper;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryPointMapper {
    DeliveryPointDto toDto(DeliveryPoint deliveryPoint);
    DeliveryPoint toEntity(DeliveryPointDto deliveryPointDto);

}
