package com.ahmed.inventory.dto.response;

import com.ahmed.inventory.entity.Vehicle;
import com.ahmed.inventory.enums.VehicleStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        String tenantId,
        UUID dealerId,
        String model,
        BigDecimal price,
        VehicleStatus status
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getTenantId(),
                vehicle.getDealerId(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.getStatus()
        );
    }
}