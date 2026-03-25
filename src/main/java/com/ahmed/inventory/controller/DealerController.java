package com.ahmed.inventory.controller;

import com.ahmed.inventory.dto.request.DealerRequest;
import com.ahmed.inventory.dto.response.DealerResponse;
import com.ahmed.inventory.service.DealerService;
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

import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    @PostMapping
    public ResponseEntity<DealerResponse> createDealer(
            @RequestBody @Validated({OnCreate.class, Default.class}) DealerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dealerService.createDealer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getDealerById(@PathVariable UUID id) {
        return ResponseEntity.ok(dealerService.getDealerById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DealerResponse>> getAllDealers(
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(dealerService.getAllDealers(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DealerResponse> updateDealer(
            @PathVariable UUID id,
            @RequestBody @Validated({OnUpdate.class, Default.class}) DealerRequest request
    ) {
        return ResponseEntity.ok(dealerService.updateDealer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable UUID id) {
        dealerService.deleteDealer(id);
        return ResponseEntity.noContent().build();
    }
}