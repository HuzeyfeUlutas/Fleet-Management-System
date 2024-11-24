package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
import com.hulutas.fleet_management_system.mapper.SackMapper;
import com.hulutas.fleet_management_system.model.Sack;
import com.hulutas.fleet_management_system.repository.SackRepository;
import com.hulutas.fleet_management_system.service.SackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SackServiceImpl implements SackService {
    private static final Logger logger = LoggerFactory.getLogger(SackServiceImpl.class);
    private final SackRepository sackRepository;

    private final SackMapper sackMapper;


    public SackServiceImpl(SackRepository sackRepository, SackMapper sackMapper) {
        this.sackRepository = sackRepository;
        this.sackMapper = sackMapper;
    }

    @Override
    public List<SackDto> getAllSack(){
        List<Sack> sacks = sackRepository.findAll();

        if(sacks.isEmpty())
            throw new ResourceNotFoundException("Sacks not found");

        return sacks.stream().map(sackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public SackDto createSack(SackDto sackDto) {
        Sack sack = sackMapper.toEntity(sackDto);

        Sack savedSack = sackRepository.save(sack);
        return sackMapper.toDto(savedSack);
    }
    @Override
    public SackDto getSackId(Long id) {
        Sack sack = sackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sack not found with id: " + id));

        return sackMapper.toDto(sack);
    }

    @Override
    public SackDto updateSack(SackDto sackDto) {
        Sack sack = sackRepository.findById(sackDto.id()).orElseThrow(() -> new ResourceNotFoundException("Sack not found with id:" + sackDto.id()));
        Sack entity = sackMapper.toEntity(sackDto);


        Sack updatedSack = sackRepository.save(entity);

        return sackMapper.toDto(updatedSack);
    }
    @Override
    public void deleteSack(Long id) {
        if (!sackRepository.existsById(id))
            throw new ResourceNotFoundException("Sack not found with id: " + id);

        sackRepository.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSacks(List<Sack> sacks) {
        sackRepository.saveAll(sacks);
    }
}
