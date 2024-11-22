package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.RootRequestDto;
import com.hulutas.fleet_management_system.dto.RouteDto;
import com.hulutas.fleet_management_system.dto.VehicleDto;
import com.hulutas.fleet_management_system.dto.VehicleLoadDto;
import com.hulutas.fleet_management_system.enums.PackageStatus;
import com.hulutas.fleet_management_system.enums.SackStatus;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.model.Package;
import com.hulutas.fleet_management_system.model.Sack;
import com.hulutas.fleet_management_system.model.Vehicle;
import com.hulutas.fleet_management_system.repository.PackageRepository;
import com.hulutas.fleet_management_system.repository.SackRepository;
import com.hulutas.fleet_management_system.repository.VehicleRepository;
import com.hulutas.fleet_management_system.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    private final VehicleRepository vehicleRepository;
    private final SackRepository sackRepository;
    private final PackageRepository packageRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, SackRepository sackRepository, PackageRepository packageRepository) {
        this.vehicleRepository = vehicleRepository;
        this.packageRepository = packageRepository;
        this.sackRepository = sackRepository;
    }


    @Override
    public List<VehicleDto> getAllVehicle() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        if (vehicles.isEmpty())
            throw new ResourceNotFoundException("Vehicles not found");

        return vehicles.stream().map(vehicle -> new VehicleDto(vehicle.getId(), vehicle.getPlateNumber(), vehicle.getSacks(), vehicle.getPackages())).collect(Collectors.toList());
    }

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(vehicleDto.plateNumber());
        vehicle.setPackages(vehicleDto.packages());
        vehicle.setSacks(vehicleDto.sacks());
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return new VehicleDto(savedVehicle.getId(), savedVehicle.getPlateNumber(), savedVehicle.getSacks(), savedVehicle.getPackages());
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        return new VehicleDto(vehicle.getId(), vehicle.getPlateNumber(), vehicle.getSacks(), vehicle.getPackages());
    }

    @Override
    public VehicleDto updateVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.id()).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id:" + vehicleDto.id()));
        vehicle.setSacks(vehicleDto.sacks());
        vehicle.setPackages(vehicleDto.packages());
        vehicle.setPlateNumber(vehicleDto.plateNumber());

        Vehicle updateVehicle = vehicleRepository.save(vehicle);

        return new VehicleDto(updateVehicle.getId(), updateVehicle.getPlateNumber(), updateVehicle.getSacks(), updateVehicle.getPackages());

    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id))
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);

        vehicleRepository.deleteById(id);
    }

    @Override
    public RootRequestDto distributePackages(String vehicleId, RootRequestDto rootRequestDto) {
        RootRequestDto response = new RootRequestDto(vehicleId, new ArrayList<>());

        for (RouteDto routeDto : rootRequestDto.route()) {
            List<String> packageBarcodes = new ArrayList<>();
            List<String> sackBarcodes = new ArrayList<>();

            for (VehicleLoadDto vehicleLoadDto : routeDto.deliveries()) {
                if (vehicleLoadDto.barcode().startsWith("P")) {
                    packageBarcodes.add(vehicleLoadDto.barcode());
                } else if (vehicleLoadDto.barcode().startsWith("C")) {
                    sackBarcodes.add(vehicleLoadDto.barcode());
                }
            }
            //Can be converted to event
            List<Package> packages = packageRepository.findByBarcodeIn(packageBarcodes);
            List<Sack> sacks = sackRepository.findByBarcodeIn(sackBarcodes);

            List<VehicleLoadDto> reponseVehicleDtos = new ArrayList<>();
            for (Package apackage : packages) {
                apackage.setStatus(PackageStatus.LOADED);
                if (apackage.getDeliveryPoint().getId() != routeDto.deliveryPoint()) {
                    System.out.println("Wrong location for barcode number: " + apackage.getBarcode() + " it must be " + apackage.getDeliveryPoint().getId() + " not be " + routeDto.deliveryPoint());
                    reponseVehicleDtos.add(new VehicleLoadDto(apackage.getBarcode(), apackage.getStatus().getValue()));
                    break;
                }
                apackage.setStatus(PackageStatus.UNLOADED);
                reponseVehicleDtos.add(new VehicleLoadDto(apackage.getBarcode(), apackage.getStatus().getValue()));
            }


            for (Sack sack : sacks) {
                sack.setStatus(SackStatus.LOADED);
                for (Package sackpackage : sack.getaPackage()) {
                    sackpackage.setStatus(PackageStatus.LOADED);
                    if (sackpackage.getDeliveryPoint().getId() != routeDto.deliveryPoint())
                        System.out.println("Wrong location for barcode number: " + sackpackage.getBarcode() + " it must be " + sackpackage.getDeliveryPoint().getId() + " not be " + routeDto.deliveryPoint());
                    sackpackage.setStatus(PackageStatus.UNLOADED);
                }
                if (sack.getDeliveryPoint().getId() != routeDto.deliveryPoint()) {
                    System.out.println("Wrong location for barcode number: " + sack.getBarcode() + " it must be " + sack.getDeliveryPoint().getId() + " not be " + routeDto.deliveryPoint());
                    reponseVehicleDtos.add(new VehicleLoadDto(sack.getBarcode(), sack.getStatus().getValue()));
                    break;
                }
                sack.setStatus(SackStatus.UNLOADED);
                reponseVehicleDtos.add(new VehicleLoadDto(sack.getBarcode(), sack.getStatus().getValue()));
            }

            response.route().add(new RouteDto(routeDto.deliveryPoint(),reponseVehicleDtos));


        }
        System.out.println("Distribution completed");

        return response;
    }
}
