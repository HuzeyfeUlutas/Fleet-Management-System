package com.hulutas.fleet_management_system.mapper;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.model.Sack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SackMapper {
    SackDto toDto(Sack sack);
    Sack toEntity(SackDto sackDto);

}
