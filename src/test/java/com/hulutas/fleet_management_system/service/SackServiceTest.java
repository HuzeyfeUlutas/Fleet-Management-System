package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.enums.SackStatus;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.SackMapper;
import com.hulutas.fleet_management_system.model.Sack;
import com.hulutas.fleet_management_system.repository.SackRepository;
import com.hulutas.fleet_management_system.service.impl.SackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SackServiceTest {
    @Mock
    private SackRepository sackRepository;
    @Mock
    private SackMapper sackMapper;
    @InjectMocks
    private SackServiceImpl sackService;

    private Sack sack;
    private SackDto sackDto;

    @BeforeEach
    void setUp() {
        sackDto = new SackDto(2L, SackStatus.CREATED, "Test",null,null,null);
        sack = new Sack();
        sack.setStatus(sackDto.status());
        sack.setBarcode(sackDto.barcode());
    }

    @Test
    void testGetAllSack() {
        when(sackRepository.findAll()).thenReturn(List.of(sack));
        when(sackMapper.toDto(sack)).thenReturn(sackDto);

        List<SackDto> result = sackService.getAllSack();

        assertNotNull(result);

        assertEquals("Test", result.get(0).barcode());
    }

    @Test
    void testGetAllSack_shouldThrowExceptionWhenSackIsNull() {
        when(sackRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            sackService.getAllSack();
        });

        assertEquals("Sacks not found", exception.getMessage());
        verify(sackRepository, times(1)).findAll();
    }

    @Test
    void testCreateSack() {
        when(sackRepository.save(any(Sack.class))).thenReturn(sack);
        when(sackMapper.toEntity(any(SackDto.class))).thenReturn(sack);
        when(sackMapper.toDto(any(Sack.class))).thenReturn(sackDto);

        SackDto result = sackService.createSack(sackDto);

        assertNotNull(result);
        assertEquals("Test", result.barcode());
        verify(sackRepository, times(1)).save(any(Sack.class));
        verify(sackMapper, times(1)).toDto(any(Sack.class));
    }

    @Test
    void testGetSackById() {
        when(sackRepository.findById(2L)).thenReturn(Optional.ofNullable(sack));
        when(sackMapper.toDto(any(Sack.class))).thenReturn(sackDto);

        SackDto result = sackService.getSackId(2L);

        assertNotNull(result);
        assertEquals("Test", result.barcode());
        verify(sackRepository, times(1)).findById(anyLong());
        verify(sackMapper, times(1)).toDto(any(Sack.class));
    }

    @Test
    void testGetSackById_shouldThrowExceptionWhenSackNotFound() {
        when(sackRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            sackService.getSackId(2L);
        });

        assertEquals("Sack not found with id: 2", exception.getMessage());
        verify(sackRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateSack() {
        SackDto updatedSackDto = new SackDto(2L, SackStatus.CREATED, "Test123",null,null,null);
        when(sackRepository.findById(2L)).thenReturn(Optional.ofNullable(sack));
        when(sackMapper.toEntity(any(SackDto.class))).thenReturn(sack);
        sack.setBarcode("Test123");
        when(sackRepository.save(sack)).thenReturn(sack);
        when(sackMapper.toDto(any(Sack.class))).thenReturn(updatedSackDto);

        SackDto result = sackService.updateSack(updatedSackDto);

        assertEquals(updatedSackDto.barcode(), sack.getBarcode());
        assertEquals("Test123", updatedSackDto.barcode());
    }

    @Test
    void testUpdateSack_shouldThrowExceptionWhenSackNotFound() {
        when(sackRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            sackService.updateSack(sackDto);
        });

        assertEquals("Sack not found with id:2", exception.getMessage());
        verify(sackRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteSack() {
        Long sackId = 1L;
        when(sackRepository.existsById(sackId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            sackService.deleteSack(sackId);
        });

        assertEquals("Sack not found with id: " + sackId, exception.getMessage());
        verify(sackRepository, never()).deleteById(sackId);
    }
}
