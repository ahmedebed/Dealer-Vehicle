package com.ahmed.inventory.dto.response;

import com.ahmed.inventory.entity.Dealer;
import com.ahmed.inventory.enums.SubscriptionType;

import java.util.UUID;

public record DealerResponse(
        UUID id,
        String tenantId,
        String name,
        String email,
        SubscriptionType subscriptionType
) {
    public static DealerResponse fromEntity(Dealer dealer) {
        return new DealerResponse(
                dealer.getId(),
                dealer.getTenantId(),
                dealer.getName(),
                dealer.getEmail(),
                dealer.getSubscriptionType()
        );
    }
}