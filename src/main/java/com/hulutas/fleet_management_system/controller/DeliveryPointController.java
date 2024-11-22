package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.DeliveryPointDto;
import com.hulutas.fleet_management_system.service.DeliveryPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryPointController {
    private final DeliveryPointService deliveryPointService;

    public DeliveryPointController(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

    @GetMapping
    public List<DeliveryPointDto> getAllDeliveryPoints(){
        return deliveryPointService.getAllDeliveryPoints();
    }

    @PostMapping
    public ResponseEntity<DeliveryPointDto> createDeliveryPoint(@RequestBody DeliveryPointDto deliveryPointDto) {
        return ResponseEntity.ok(deliveryPointService.createDeliveryPoint(deliveryPointDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPointDto> getDeliveryPointById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointById(id));
    }
    @PutMapping()
    public ResponseEntity<DeliveryPointDto> updateDeliveryPoint(@RequestBody DeliveryPointDto deliveryPointDto) {
        return ResponseEntity.ok(deliveryPointService.updateDeliveryPoint(deliveryPointDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeliveryPointDto> deleteDeliveryPoint(@PathVariable Long id) {
        deliveryPointService.deleteDeliveryPoint(id);
        return ResponseEntity.noContent().build();
    }
}
