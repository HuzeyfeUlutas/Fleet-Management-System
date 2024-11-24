package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.RootRequestDto;
import com.hulutas.fleet_management_system.dto.VehicleDto;
import com.hulutas.fleet_management_system.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")

public class VehicleController {
    private final VehicleService vehicleService;


    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping()
    public List<VehicleDto> getAllVehicles(){
        return vehicleService.getAllVehicle();
    }

    @PostMapping
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicleDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }
    @PutMapping()
    public ResponseEntity<VehicleDto> updatePackage(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<VehicleDto> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{vehicleId}/distribute")
    public ResponseEntity<RootRequestDto> distributePackages( @PathVariable("vehicleId") String vehicleId, @RequestBody RootRequestDto rootRequestDto) {
        return ResponseEntity.ok(vehicleService.distributePackages(vehicleId, rootRequestDto));
    }
}
