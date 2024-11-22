package com.hulutas.fleet_management_system.controller;

import com.hulutas.fleet_management_system.dto.SackDto;
import com.hulutas.fleet_management_system.service.SackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sack")
public class SackController {
    private final SackService sackService;

    public SackController(SackService sackService) {
        this.sackService = sackService;
    }

    @GetMapping
    public List<SackDto> getAllSacks(){
        return sackService.getAllSack();
    }

    @PostMapping
    public ResponseEntity<SackDto> createPackage(@RequestBody SackDto sackDto) {
        return ResponseEntity.ok(sackService.createSack(sackDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SackDto> getSackById(@PathVariable Long id) {
        return ResponseEntity.ok(sackService.getSackId(id));
    }
    @PutMapping()
    public ResponseEntity<SackDto> updateSack(@RequestBody SackDto sackDto) {
        return ResponseEntity.ok(sackService.updateSack(sackDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<SackDto> deleteSack(@PathVariable Long id) {
        sackService.deleteSack(id);
        return ResponseEntity.noContent().build();
    }
}