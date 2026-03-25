package com.ahmed.inventory.service;

import com.ahmed.inventory.dto.request.DealerRequest;
import com.ahmed.inventory.dto.response.DealerResponse;
import com.ahmed.inventory.entity.Dealer;
import com.ahmed.inventory.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepo;
    private final GlobalService globalService;

    public DealerResponse createDealer(DealerRequest request) {
        String tenantId = globalService.getCurrentTenantId();

        Dealer dealer = Dealer.builder()
                .id(UUID.randomUUID())
                .tenantId(tenantId)
                .name(request.name())
                .email(request.email())
                .subscriptionType(request.subscriptionType())
                .build();

        dealerRepo.save(dealer);

        return DealerResponse.fromEntity(dealer);
    }

    public DealerResponse getDealerById(UUID id) {
        Dealer dealer = globalService.getDealerOrThrow(id);
        return DealerResponse.fromEntity(dealer);
    }

    public Page<DealerResponse> getAllDealers(Pageable pageable) {
        String tenantId = globalService.getCurrentTenantId();

        return dealerRepo.findAllByTenantId(tenantId, pageable)
                .map(DealerResponse::fromEntity);
    }

    public DealerResponse updateDealer(UUID id, DealerRequest request) {
        Dealer dealer = globalService.getDealerOrThrow(id);

        if (request.name() != null && !request.name().isBlank()) {
            dealer.setName(request.name());
        }

        if (request.email() != null && !request.email().isBlank()) {
            dealer.setEmail(request.email());
        }

        if (request.subscriptionType() != null) {
            dealer.setSubscriptionType(request.subscriptionType());
        }

        dealerRepo.save(dealer);

        return DealerResponse.fromEntity(dealer);
    }

    public void deleteDealer(UUID id) {
        Dealer dealer = globalService.getDealerOrThrow(id);
        dealerRepo.delete(dealer);
    }
}