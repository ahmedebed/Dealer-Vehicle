package com.ahmed.inventory.repository;

import com.ahmed.inventory.entity.Dealer;
import com.ahmed.inventory.enums.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {

    Optional<Dealer> findById(UUID id);

    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

    boolean existsByIdAndTenantId(UUID id, String tenantId);

    long countBySubscriptionType(SubscriptionType subscriptionType);

    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);

    List<Dealer> findAllByTenantIdAndSubscriptionType(String tenantId, SubscriptionType subscriptionType);
}