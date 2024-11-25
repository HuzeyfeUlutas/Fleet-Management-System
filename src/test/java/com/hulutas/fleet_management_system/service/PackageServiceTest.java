package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.PackageDto;
import com.hulutas.fleet_management_system.enums.PackageStatus;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.PackageMapper;
import com.hulutas.fleet_management_system.model.Package;
import com.hulutas.fleet_management_system.repository.PackageRepository;
import com.hulutas.fleet_management_system.service.impl.PackageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {
    @Mock
    private PackageRepository packageRepository;
    @Mock
    private PackageMapper packageMapper;
    @InjectMocks
    private PackageServiceImpl packageService;

    private Package aPackage;
    private PackageDto packageDto;

    @BeforeEach
    void setUp() {

        packageDto = new PackageDto(2L, PackageStatus.CREATED, "Test",1,null,null,null);

        aPackage = new Package();
        aPackage.setStatus(packageDto.packageStatus());
        aPackage.setBarcode(packageDto.barcode());
        aPackage.setDesi(packageDto.desi());
    }

    @Test
    void testGetAllPackage() {
        when(packageRepository.findAll()).thenReturn(List.of(aPackage));
        when(packageMapper.toDto(aPackage)).thenReturn(packageDto);

        List<PackageDto> result = packageService.getAllPackage();

        assertNotNull(result);

        assertEquals("Test", result.get(0).barcode());
    }

    @Test
    void testGetAllPackage_shouldThrowExceptionWhenPackageIsNull() {
        when(packageRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            packageService.getAllPackage();
        });

        assertEquals("Packages not found", exception.getMessage());
        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void testCreatePackage() {
        when(packageRepository.save(any(Package.class))).thenReturn(aPackage);
        when(packageMapper.toEntity(any(PackageDto.class))).thenReturn(aPackage);
        when(packageMapper.toDto(any(Package.class))).thenReturn(packageDto);

        PackageDto result = packageService.createPackage(packageDto);

        assertNotNull(result);
        assertEquals("Test", result.barcode());
        verify(packageRepository, times(1)).save(any(Package.class));
        verify(packageMapper, times(1)).toDto(any(Package.class));
    }

    @Test
    void testGetPackageById() {
        when(packageRepository.findById(2L)).thenReturn(Optional.ofNullable(aPackage));
        when(packageMapper.toDto(any(Package.class))).thenReturn(packageDto);

        PackageDto result = packageService.getPackageById(2L);

        assertNotNull(result);
        assertEquals("Test", result.barcode());
        verify(packageRepository, times(1)).findById(anyLong());
        verify(packageMapper, times(1)).toDto(any(Package.class));
    }

    @Test
    void testGetPackageById_shouldThrowExceptionWhenPackageNotFound() {
        when(packageRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            packageService.getPackageById(2L);
        });

        assertEquals("Package not found with id: 2", exception.getMessage());
        verify(packageRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdatePackage() {
        PackageDto updatedPackageDto = new PackageDto(2L, PackageStatus.CREATED, "Test123",1,null,null,null);
        when(packageRepository.findById(2L)).thenReturn(Optional.ofNullable(aPackage));
        when(packageMapper.toEntity(any(PackageDto.class))).thenReturn(aPackage);
        aPackage.setBarcode("Test123");
        when(packageRepository.save(aPackage)).thenReturn(aPackage);
        when(packageMapper.toDto(any(Package.class))).thenReturn(updatedPackageDto);

        PackageDto result = packageService.updatePackage(updatedPackageDto);

        assertEquals(updatedPackageDto.barcode(), aPackage.getBarcode());
        assertEquals("Test123", updatedPackageDto.barcode());
    }

    @Test
    void testUpdatePackage_shouldThrowExceptionWhenPackageNotFound() {
        when(packageRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            packageService.updatePackage(packageDto);
        });

        assertEquals("Package not found with id:2", exception.getMessage());
        verify(packageRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeletePackage() {
        Long packageId = 1L;
        when(packageRepository.existsById(packageId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            packageService.deletePackage(packageId);
        });

        assertEquals("Package not found with id: " + packageId, exception.getMessage());
        verify(packageRepository, never()).deleteById(packageId);
    }
}
