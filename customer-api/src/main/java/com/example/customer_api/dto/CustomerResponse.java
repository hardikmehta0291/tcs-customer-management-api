package com.example.customer_api.dto;

import com.example.customer_api.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private UUID id;
    private String name;
    private String email;
    private BigDecimal annualSpend;
    private LocalDateTime lastPurchaseDate;
    private String tier;

    public static CustomerResponse fromCustomer(Customer customer, String tier) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .annualSpend(customer.getAnnualSpend())
                .lastPurchaseDate(customer.getLastPurchaseDate())
                .tier(tier)
                .build();
    }

    public static CustomerResponse of(Customer customer, String tier) {
        return fromCustomer(customer, tier);
    }
}

