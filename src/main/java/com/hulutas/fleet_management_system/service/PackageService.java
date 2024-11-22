package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.PackageDto;

import java.util.List;

public interface PackageService {

    List<PackageDto> getAllPackage();

    PackageDto createPackage(PackageDto packageDtoDto);

    PackageDto getPackageById(Long id);

    PackageDto updatePackage(PackageDto packageDto);

    void deletePackage(Long id);
}
