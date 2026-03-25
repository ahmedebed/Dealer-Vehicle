package com.ahmed.inventory.controller;

import com.ahmed.inventory.dto.request.VehicleRequest;
import com.ahmed.inventory.dto.response.VehicleResponse;
import com.ahmed.inventory.enums.VehicleStatus;
import com.ahmed.inventory.service.VehicleService;
import com.ahmed.inventory.validation.OnCreate;
import com.ahmed.inventory.validation.OnUpdate;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @RequestBody @Validated({OnCreate.class, Default.class}) VehicleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) String subscription,
            @PageableDefault(page = 0, size = 10, sort = "model") Pageable pageable
    ) {
        return ResponseEntity.ok(
                vehicleService.getAllVehicles(model, status, priceMin, priceMax, subscription, pageable)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable UUID id,
            @RequestBody @Validated({OnUpdate.class, Default.class}) VehicleRequest request
    ) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}