package com.hulutas.fleet_management_system.repository;

import com.hulutas.fleet_management_system.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
