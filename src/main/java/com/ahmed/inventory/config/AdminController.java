package com.ahmed.inventory.controller;

import com.ahmed.inventory.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dealers/countBySubscription")
    public ResponseEntity<Map<String, Long>> countBySubscription() {
        return ResponseEntity.ok(adminService.countDealersBySubscription());
    }
}