package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.DeliveryPointMapper;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.repository.DeliveryPointRepository;
import com.hulutas.fleet_management_system.service.impl.DeliveryPointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryPointServiceTest {
    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private DeliveryPointMapper deliveryPointMapper;

    @InjectMocks
    private DeliveryPointServiceImpl deliveryPointService;

    private DeliveryPointDto deliveryPointDto;
    private DeliveryPoint deliveryPoint;

    @BeforeEach
    void setUp() {
        deliveryPointDto = new DeliveryPointDto(2L, "destination", null, null);

        deliveryPoint = new DeliveryPoint();
        deliveryPoint.setName(deliveryPointDto.name());
        deliveryPoint.setId(deliveryPointDto.id());
    }

    @Test
    void testGetAllDeliveryPoints() {
        when(deliveryPointRepository.findAll()).thenReturn(List.of(deliveryPoint));

        when(deliveryPointMapper.toDto(deliveryPoint)).thenReturn(deliveryPointDto);

        List<DeliveryPointDto> result = deliveryPointService.getAllDeliveryPoints();

        assertNotNull(result);
        // Assert
        assertEquals("destination", result.get(0).name());
    }

    @Test
    void testGetAllDeliveryPoints_shouldThrowExceptionWhenDeliveryPointsIsNull() {
        when(deliveryPointRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deliveryPointService.getAllDeliveryPoints();
        });

        assertEquals("Delivery points not found", exception.getMessage());
        verify(deliveryPointRepository, times(1)).findAll();
    }

    @Test
    void testCreateDeliveryPoint() {
        when(deliveryPointRepository.save(any(DeliveryPoint.class))).thenReturn(deliveryPoint);
        when(deliveryPointMapper.toDto(any(DeliveryPoint.class))).thenReturn(deliveryPointDto);

        DeliveryPointDto result = deliveryPointService.createDeliveryPoint(deliveryPointDto);

        assertNotNull(result);
        assertEquals("destination", result.name());
        verify(deliveryPointRepository, times(1)).save(any(DeliveryPoint.class));
        verify(deliveryPointMapper, times(1)).toDto(any(DeliveryPoint.class));
    }

    @Test
    void testGetDeliveryPointById() {
        when(deliveryPointRepository.findById(deliveryPoint.getId())).thenReturn(Optional.ofNullable(deliveryPoint));
        when(deliveryPointMapper.toDto(any(DeliveryPoint.class))).thenReturn(deliveryPointDto);

        DeliveryPointDto result = deliveryPointService.getDeliveryPointById(deliveryPoint.getId());

        assertNotNull(result);
        assertEquals(2L, result.id());
    }

    @Test
    void testGetDeliveryPointById_shouldThrowExceptionWhenDeliveryNotFound() {
        when(deliveryPointRepository.findById(deliveryPoint.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deliveryPointService.getDeliveryPointById(deliveryPoint.getId());
        });

        assertEquals("Deliverypoint not found with id: " + deliveryPointDto.id(), exception.getMessage());
        verify(deliveryPointRepository, times(1)).findById(deliveryPoint.getId());
    }

    @Test
    void testUpdateDeliveryPoint(){
        Long deliveryPointId = 1L;
        DeliveryPointDto deliveryPointDto = new DeliveryPointDto(deliveryPointId, "Updated Delivery Point", null, null);

        DeliveryPoint existingDeliveryPoint = new DeliveryPoint();
        existingDeliveryPoint.setId(deliveryPointId);
        existingDeliveryPoint.setName("Old Delivery Point");

        when(deliveryPointRepository.findById(deliveryPointId)).thenReturn(Optional.of(existingDeliveryPoint));

        DeliveryPoint entity = new DeliveryPoint();
        entity.setId(deliveryPointId);
        entity.setName("Updated Delivery Point");

        when(deliveryPointMapper.toEntity(deliveryPointDto)).thenReturn(entity);

        when(deliveryPointRepository.save(entity)).thenReturn(entity);

        when(deliveryPointMapper.toDto(entity)).thenReturn(deliveryPointDto);

        DeliveryPointDto updatedDeliveryPointDto = deliveryPointService.updateDeliveryPoint(deliveryPointDto);

        assertEquals(deliveryPointDto.id(), updatedDeliveryPointDto.id());
        assertEquals("Updated Delivery Point", updatedDeliveryPointDto.name());

        verify(deliveryPointRepository, times(1)).findById(deliveryPointId);  // Verify the findById method was called once
        verify(deliveryPointRepository, times(1)).save(entity);

    }
}
