package com.hulutas.fleet_management_system.mapper;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    Package INSTANCE = Mappers.getMapper(Package.class);

    @Mapping(target = "deliveryPoint", ignore = true)
    PackageDto toDto(Package apackage);
    Package toEntity(PackageDto packageDto);

}