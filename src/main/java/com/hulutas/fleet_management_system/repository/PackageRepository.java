package com.hulutas.fleet_management_system.repository;

import com.hulutas.fleet_management_system.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findByBarcodeIn(List<String> barcodes);
}
