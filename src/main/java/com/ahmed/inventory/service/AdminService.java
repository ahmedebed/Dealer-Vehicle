package com.ahmed.inventory.service;

import com.ahmed.inventory.enums.SubscriptionType;
import com.ahmed.inventory.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DealerRepository dealerRepository;

    public Map<String, Long> countDealersBySubscription() {
        long basicCount = dealerRepository.countBySubscriptionType(SubscriptionType.BASIC);
        long premiumCount = dealerRepository.countBySubscriptionType(SubscriptionType.PREMIUM);

        return Map.of(
                "BASIC", basicCount,
                "PREMIUM", premiumCount
        );
    }
}