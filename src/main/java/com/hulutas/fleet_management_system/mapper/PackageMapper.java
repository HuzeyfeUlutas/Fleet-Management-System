package com.hulutas.fleet_management_system.mapper;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    PackageDto toDto(Package apackage);
    Package toEntity(PackageDto packageDto);

}