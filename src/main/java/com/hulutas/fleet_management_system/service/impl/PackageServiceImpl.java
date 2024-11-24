package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.PackageMapper;
import com.hulutas.fleet_management_system.model.Package;
import com.hulutas.fleet_management_system.repository.PackageRepository;
import com.hulutas.fleet_management_system.service.PackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;

    private final PackageMapper packageMapper;

    public PackageServiceImpl(PackageRepository packageRepository, PackageMapper packageMapper) {
        this.packageRepository = packageRepository;
        this.packageMapper = packageMapper;
    }


    @Override
    public List<PackageDto> getAllPackage(){
        List<Package> packages = packageRepository.findAll();

        if(packages.isEmpty())
            throw new ResourceNotFoundException("Packages not found");

        return packages.stream().map(packageMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PackageDto createPackage(PackageDto packageDto) {
        Package aPackage = packageMapper.toEntity(packageDto);
        Package savedPackage = packageRepository.save(aPackage);
        return packageMapper.toDto(savedPackage);
    }

    @Override
    public PackageDto getPackageById(Long id) {
        Package aPackage = packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Package not found with id: " + id));

        return packageMapper.toDto(aPackage);
    }
    @Override
    public PackageDto updatePackage(PackageDto packageDto) {
        Package apackage = packageRepository.findById(packageDto.id()).orElseThrow(() -> new ResourceNotFoundException("Package not found with id:" + packageDto.id()));
        Package entity = packageMapper.toEntity(packageDto);

        Package updatedPackage = packageRepository.save(entity);

        return packageMapper.toDto(updatedPackage);

    }
    @Override
    public void deletePackage(Long id) {
        if (!packageRepository.existsById(id))
            throw new ResourceNotFoundException("Package not found with id: " + id);

        packageRepository.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePackages(List<Package> packages) {
        packageRepository.saveAll(packages);
    }
}
