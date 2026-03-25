package com.ahmed.inventory.repository;

import com.ahmed.inventory.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {

    Optional<Vehicle> findById(UUID id);

    Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId);

    boolean existsByIdAndTenantId(UUID id, String tenantId);
}