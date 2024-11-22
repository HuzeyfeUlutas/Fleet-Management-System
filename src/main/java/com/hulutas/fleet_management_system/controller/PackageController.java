package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public List<PackageDto> getAllPackages(){
        return packageService.getAllPackage();
    }
    @PostMapping
    public ResponseEntity<PackageDto> createPackage(@RequestBody PackageDto packageDto) {
        return ResponseEntity.ok(packageService.createPackage(packageDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable Long id) {
        return ResponseEntity.ok(packageService.getPackageById(id));
    }
    @PutMapping()
    public ResponseEntity<PackageDto> updatePackage(@RequestBody PackageDto packageDto) {
        return ResponseEntity.ok(packageService.updatePackage(packageDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PackageDto> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}