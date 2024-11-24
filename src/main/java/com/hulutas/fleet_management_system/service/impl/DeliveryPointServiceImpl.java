package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.DeliveryPointMapper;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.repository.DeliveryPointRepository;
import com.hulutas.fleet_management_system.service.DeliveryPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryPointServiceImpl implements DeliveryPointService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryPointServiceImpl.class);
    private final DeliveryPointRepository deliveryPointRepository;
    private final DeliveryPointMapper deliveryPointMapper;

    public DeliveryPointServiceImpl(DeliveryPointRepository deliveryPointRepository, DeliveryPointMapper deliveryPointMapper) {
        this.deliveryPointRepository = deliveryPointRepository;
        this.deliveryPointMapper = deliveryPointMapper;
    }

    @Override
    public List<DeliveryPointDto> getAllDeliveryPoints() {
        List<DeliveryPoint> deliveryPoints = deliveryPointRepository.findAll();

        if (deliveryPoints.isEmpty())
            throw new ResourceNotFoundException("Delivery points not found");

        return deliveryPoints.stream().map(deliveryPointMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DeliveryPointDto createDeliveryPoint(DeliveryPointDto deliveryPointDto) {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setName(deliveryPointDto.name());
        DeliveryPoint savedDeliveryPoint = deliveryPointRepository.save(deliveryPoint);
        return deliveryPointMapper.toDto(savedDeliveryPoint);
    }

    @Override
    public DeliveryPointDto getDeliveryPointById(Long id) {
        DeliveryPoint deliveryPoint = deliveryPointRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Deliverypoint not found with id: " + id));

        return deliveryPointMapper.toDto(deliveryPoint);
    }

    @Override
    public DeliveryPointDto updateDeliveryPoint(DeliveryPointDto deliveryPointDto) {
        deliveryPointRepository.findById(deliveryPointDto.id()).orElseThrow(() -> new ResourceNotFoundException("Deliverypoint not found with id:" + deliveryPointDto.id()));
        DeliveryPoint entity = deliveryPointMapper.toEntity(deliveryPointDto);

        DeliveryPoint updatedDeliveryPoint = deliveryPointRepository.save(entity);

        return deliveryPointMapper.toDto(updatedDeliveryPoint);

    }

    @Override
    public boolean checkPackageIsAllowed(int deliveryPointId) {
        return deliveryPointId != 3;
    }

    @Override
    public boolean checkSackIsAllowed(int deliveryPointId) {
        return deliveryPointId != 1;
    }

    @Override
    public void deleteDeliveryPoint(Long id) {
        if (!deliveryPointRepository.existsById(id))
            throw new ResourceNotFoundException("Deliverypoint not found with id: " + id);

        deliveryPointRepository.deleteById(id);
    }

}
