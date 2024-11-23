package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.mapper.DeliveryPointMapper;
import com.hulutas.fleet_management_system.model.DeliveryPoint;
import com.hulutas.fleet_management_system.repository.DeliveryPointRepository;
import com.hulutas.fleet_management_system.service.impl.DeliveryPointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryPointServiceTest {
    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private DeliveryPointMapper deliveryPointMapper;

    @InjectMocks
    private DeliveryPointServiceImpl deliveryPointService;

    @Test
    void testGetAllDeliveryPoints() {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setName("destination");
        DeliveryPointDto deliveryPointDto = new DeliveryPointDto(deliveryPoint.getId(), deliveryPoint.getName(), null, null);

        when(deliveryPointRepository.findAll()).thenReturn(List.of(deliveryPoint));

        when(deliveryPointMapper.toDto(deliveryPoint)).thenReturn(deliveryPointDto);

        List<DeliveryPointDto> result = deliveryPointService.getAllDeliveryPoints();

        assertNotNull(result);
        // Assert
        assertEquals("destination", result.get(0).name());
    }
}
