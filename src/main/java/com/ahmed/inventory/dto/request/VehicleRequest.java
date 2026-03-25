package com.ahmed.inventory.dto.request;


import com.ahmed.inventory.enums.VehicleStatus;
import com.ahmed.inventory.validation.OnCreate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleRequest(

        @NotNull(message = "Dealer id is required", groups = OnCreate.class)
        UUID dealerId,

        @NotBlank(message = "Vehicle model is required", groups = OnCreate.class)
        String model,

        @NotNull(message = "Vehicle price is required", groups = OnCreate.class)
        @DecimalMin(value = "0.0", inclusive = true, message = "Vehicle price must be greater than or equal to 0")
        BigDecimal price,

        @NotNull(message = "Vehicle status is required", groups = OnCreate.class)
        VehicleStatus status

) {
}