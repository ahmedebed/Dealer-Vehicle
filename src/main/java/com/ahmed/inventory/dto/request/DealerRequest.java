package com.ahmed.inventory.dto.request;


import com.ahmed.inventory.enums.SubscriptionType;
import com.ahmed.inventory.validation.OnCreate;
import com.ahmed.inventory.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DealerRequest(

        @NotBlank(message = "Dealer name is required", groups = OnCreate.class)
        String name,

        @NotBlank(message = "Dealer email is required", groups = OnCreate.class)
        @Email(message = "Dealer email must be valid", groups = {OnCreate.class, OnUpdate.class})
        String email,

        @NotNull(message = "Subscription type is required", groups = OnCreate.class)
        SubscriptionType subscriptionType

) {
}