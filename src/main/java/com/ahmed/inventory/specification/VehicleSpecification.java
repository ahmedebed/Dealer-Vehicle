package com.ahmed.inventory.specification;

import com.ahmed.inventory.entity.Vehicle;
import com.ahmed.inventory.enums.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class VehicleSpecification {

    public static Specification<Vehicle> hasTenantId(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Vehicle> hasModel(String model) {
        return (root, query, cb) ->
                model == null || model.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    public static Specification<Vehicle> hasStatus(VehicleStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Vehicle> priceGreaterThanOrEqual(BigDecimal priceMin) {
        return (root, query, cb) ->
                priceMin == null ? null : cb.greaterThanOrEqualTo(root.get("price"), priceMin);
    }

    public static Specification<Vehicle> priceLessThanOrEqual(BigDecimal priceMax) {
        return (root, query, cb) ->
                priceMax == null ? null : cb.lessThanOrEqualTo(root.get("price"), priceMax);
    }
}