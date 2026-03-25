package com.ahmed.inventory.service;

import com.ahmed.inventory.dto.request.VehicleRequest;
import com.ahmed.inventory.dto.response.VehicleResponse;
import com.ahmed.inventory.entity.Dealer;
import com.ahmed.inventory.entity.Vehicle;
import com.ahmed.inventory.enums.SubscriptionType;
import com.ahmed.inventory.enums.VehicleStatus;
import com.ahmed.inventory.exception.BadRequestException;
import com.ahmed.inventory.repository.DealerRepository;
import com.ahmed.inventory.repository.VehicleRepository;
import com.ahmed.inventory.specification.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepo;
    private final DealerRepository dealerRepo;
    private final GlobalService globalService;

    public VehicleResponse createVehicle(VehicleRequest request) {
        String tenantId = globalService.getCurrentTenantId();

        Dealer dealer = globalService.getDealerOrThrow(request.dealerId());

        Vehicle vehicle = Vehicle.builder()
                .id(UUID.randomUUID())
                .tenantId(tenantId)
                .dealerId(dealer.getId())
                .model(request.model())
                .price(request.price())
                .status(request.status())
                .build();

        vehicleRepo.save(vehicle);

        return VehicleResponse.fromEntity(vehicle);
    }

    public VehicleResponse getVehicleById(UUID id) {
        Vehicle vehicle = globalService.getVehicleOrThrow(id);
        return VehicleResponse.fromEntity(vehicle);
    }

    public Page<VehicleResponse> getAllVehicles(
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax,
            String subscription,
            Pageable pageable
    ) {
        String tenantId = globalService.getCurrentTenantId();

        Specification<Vehicle> specification = Specification.<Vehicle>unrestricted()
                .and(VehicleSpecification.hasTenantId(tenantId))
                .and(VehicleSpecification.hasModel(model))
                .and(VehicleSpecification.hasStatus(status))
                .and(VehicleSpecification.priceGreaterThanOrEqual(priceMin))
                .and(VehicleSpecification.priceLessThanOrEqual(priceMax));

        if (subscription != null && !subscription.isBlank()) {
            SubscriptionType subscriptionType;
            try {
                subscriptionType = SubscriptionType.valueOf(subscription.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid subscription value");
            }

            List<Dealer> dealers = dealerRepo.findAllByTenantIdAndSubscriptionType(tenantId, subscriptionType);
            Set<UUID> dealerIds = dealers.stream()
                    .map(Dealer::getId)
                    .collect(Collectors.toSet());

            if (dealerIds.isEmpty()) {
                return Page.empty(pageable);
            }

            specification = specification.and((root, query, cb) -> root.get("dealerId").in(dealerIds));
        }

        return vehicleRepo.findAll(specification, pageable)
                .map(VehicleResponse::fromEntity);
    }

    public VehicleResponse updateVehicle(UUID id, VehicleRequest request) {
        Vehicle vehicle = globalService.getVehicleOrThrow(id);

        if (request.dealerId() != null) {
            Dealer dealer = globalService.getDealerOrThrow(request.dealerId());
            vehicle.setDealerId(dealer.getId());
        }

        if (request.model() != null && !request.model().isBlank()) {
            vehicle.setModel(request.model());
        }

        if (request.price() != null) {
            vehicle.setPrice(request.price());
        }

        if (request.status() != null) {
            vehicle.setStatus(request.status());
        }

        vehicleRepo.save(vehicle);

        return VehicleResponse.fromEntity(vehicle);
    }

    public void deleteVehicle(UUID id) {
        Vehicle vehicle = globalService.getVehicleOrThrow(id);
        vehicleRepo.delete(vehicle);
    }
}