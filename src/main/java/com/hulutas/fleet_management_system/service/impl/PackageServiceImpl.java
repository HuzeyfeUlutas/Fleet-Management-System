package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
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
    private static final Logger logger = LoggerFactory.getLogger(DeliveryPointServiceImpl.class);
    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }


    @Override
    public List<PackageDto> getAllPackage(){
        List<Package> packages = packageRepository.findAll();

        if(packages.isEmpty())
            throw new ResourceNotFoundException("Packages not found");

        return packages.stream().map(pack -> new PackageDto(pack.getId(),pack.getStatus(), pack.getBarcode(), pack.getDesi(), pack.getDeliveryPoint(), pack.getSack(), pack.getVehicle())).collect(Collectors.toList());
    }

    @Override
    public PackageDto createPackage(PackageDto packageDtoDto) {
        Package aPackage = new Package();
        aPackage.setStatus(packageDtoDto.packageStatus());
        aPackage.setBarcode(packageDtoDto.barcode());
        aPackage.setDesi(packageDtoDto.desi());
        aPackage.setDeliveryPoint(packageDtoDto.deliveryPoint());
        aPackage.setVehicle(packageDtoDto.vehicle());
        Package savedPackage = packageRepository.save(aPackage);
        return new PackageDto(savedPackage.getId(), savedPackage.getStatus(), savedPackage.getBarcode(), savedPackage.getDesi(), savedPackage.getDeliveryPoint(), savedPackage.getSack(), savedPackage.getVehicle());
    }

    @Override
    public PackageDto getPackageById(Long id) {
        Package aPackage = packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Package not found with id: " + id));

        return new PackageDto(aPackage.getId(), aPackage.getStatus(), aPackage.getBarcode(), aPackage.getDesi(), aPackage.getDeliveryPoint(), aPackage.getSack(), aPackage.getVehicle());
    }
    @Override
    public PackageDto updatePackage(PackageDto packageDto) {
        Package apackage = packageRepository.findById(packageDto.id()).orElseThrow(() -> new ResourceNotFoundException("Package not found with id:" + packageDto.id()));
        apackage.setVehicle(packageDto.vehicle());
        apackage.setDeliveryPoint(packageDto.deliveryPoint());
        apackage.setDesi(packageDto.desi());
        apackage.setBarcode(packageDto.barcode());
        apackage.setSack(packageDto.sack());
        apackage.setStatus(packageDto.packageStatus());

        Package updatedPackage = packageRepository.save(apackage);

        return new PackageDto(updatedPackage.getId(), updatedPackage.getStatus(), updatedPackage.getBarcode(), updatedPackage.getDesi(),updatedPackage.getDeliveryPoint(), updatedPackage.getSack(), updatedPackage.getVehicle());

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
