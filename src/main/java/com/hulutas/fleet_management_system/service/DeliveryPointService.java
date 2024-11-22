package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;

import java.util.List;

public interface DeliveryPointService {
    List<DeliveryPointDto> getAllDeliveryPoints();

    DeliveryPointDto createDeliveryPoint(DeliveryPointDto deliveryPointDto);

    DeliveryPointDto getDeliveryPointById(Long id);

    DeliveryPointDto updateDeliveryPoint(DeliveryPointDto deliveryPointDto);

    void deleteDeliveryPoint(Long id);
}
