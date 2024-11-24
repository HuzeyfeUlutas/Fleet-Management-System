package com.hulutas.fleet_management_system.service.impl;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.exception.ResourceNotFoundException;
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


    public SackServiceImpl(SackRepository sackRepository) {
        this.sackRepository = sackRepository;
    }

    @Override
    public List<SackDto> getAllSack(){
        List<Sack> sacks = sackRepository.findAll();

        if(sacks.isEmpty())
            throw new ResourceNotFoundException("Sacks not found");

        return sacks.stream().map(sack -> new SackDto(sack.getId() ,sack.getStatus(), sack.getBarcode(), sack.getDeliveryPoint(), sack.getaPackage(), sack.getVehicle())).collect(Collectors.toList());
    }

    @Override
    public SackDto createSack(SackDto sackDto) {
        Sack sack = new Sack();
        sack.setaPackage(sackDto.packages());
        sack.setBarcode(sackDto.barcode());
        sack.setDeliveryPoint(sackDto.deliveryPoint());
        sack.setVehicle(sackDto.vehicle());
        sack.setStatus(sackDto.status());

        Sack savedSack = sackRepository.save(sack);
        return new SackDto(savedSack.getId(), savedSack.getStatus(), savedSack.getBarcode(), savedSack.getDeliveryPoint(), savedSack.getaPackage(), savedSack.getVehicle());
    }
    @Override
    public SackDto getSackId(Long id) {
        Sack sack = sackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sack not found with id: " + id));

        return new SackDto(sack.getId(), sack.getStatus(), sack.getBarcode(), sack.getDeliveryPoint(), sack.getaPackage(), sack.getVehicle());
    }

    @Override
    public SackDto updateSack(SackDto sackDto) {
        Sack sack = sackRepository.findById(sackDto.id()).orElseThrow(() -> new ResourceNotFoundException("Sack not found with id:" + sackDto.id()));
        sack.setStatus(sackDto.status());
        sack.setVehicle(sackDto.vehicle());
        sack.setDeliveryPoint(sackDto.deliveryPoint());
        sack.setBarcode(sackDto.barcode());
        sack.setaPackage(sackDto.packages());


        Sack updatedSack = sackRepository.save(sack);

        return new SackDto(updatedSack.getId(), updatedSack.getStatus(), updatedSack.getBarcode(), updatedSack.getDeliveryPoint(), updatedSack.getaPackage(), updatedSack.getVehicle());

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
