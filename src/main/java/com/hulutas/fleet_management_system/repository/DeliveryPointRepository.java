package com.hulutas.fleet_management_system.repository;

import com.hulutas.fleet_management_system.model.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Long> {
}
