package com.hulutas.fleet_management_system.repository;

import com.hulutas.fleet_management_system.model.Sack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SackRepository extends JpaRepository<Sack, Long> {
    List<Sack> findByBarcodeIn(List<String> barcodes);
}
