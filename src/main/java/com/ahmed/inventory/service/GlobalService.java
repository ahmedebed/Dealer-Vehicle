package com.ahmed.inventory.service;

import com.ahmed.inventory.entity.Dealer;
import com.ahmed.inventory.entity.Vehicle;
import com.ahmed.inventory.exception.BadRequestException;
import com.ahmed.inventory.exception.ForbiddenException;
import com.ahmed.inventory.exception.NotFoundException;
import com.ahmed.inventory.repository.DealerRepository;
import com.ahmed.inventory.repository.VehicleRepository;
import com.ahmed.inventory.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GlobalService {

    private final DealerRepository dealerRepository;
    private final VehicleRepository vehicleRepository;

    public String getCurrentTenantId() {
        String tenantId = TenantContext.getTenantId();

        if (!StringUtils.hasText(tenantId)) {
            throw new BadRequestException("Missing required header: X-Tenant-Id");
        }

        return tenantId;
    }

    public Dealer getDealerOrThrow(UUID id) {
        String tenantId = getCurrentTenantId();

        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dealer not found"));

        if (!dealer.getTenantId().equals(tenantId)) {
            throw new ForbiddenException("Cross-tenant access is forbidden");
        }

        return dealer;
    }

    public Vehicle getVehicleOrThrow(UUID id) {
        String tenantId = getCurrentTenantId();

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (!vehicle.getTenantId().equals(tenantId)) {
            throw new ForbiddenException("Cross-tenant access is forbidden");
        }

        return vehicle;
    }
}