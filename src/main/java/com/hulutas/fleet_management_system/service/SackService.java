package com.hulutas.fleet_management_system.service;

import com.hulutas.fleet_management_system.dto.SackDto;

import java.util.List;

public interface SackService {

    List<SackDto> getAllSack();

    SackDto createSack(SackDto sackDto);

    SackDto getSackId(Long id);

    SackDto updateSack(SackDto sackDto);

    void deleteSack(Long id);
}
