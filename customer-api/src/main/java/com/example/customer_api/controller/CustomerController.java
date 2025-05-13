package com.example.customer_api.controller;

import com.example.customer_api.dto.CustomerResponse;
import com.example.customer_api.model.Customer;
import com.example.customer_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid Customer customer) {
        Customer created = service.createCustomer(customer);
        return ResponseEntity.status(201).body(CustomerResponse.of(created, service.calculateTier(created)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable UUID id) {
        Customer found = service.getCustomerById(id);
        return ResponseEntity.ok(CustomerResponse.of(found, service.calculateTier(found)));
    }

    @GetMapping
    public ResponseEntity<?> getByParam(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String email) {
        if (name != null) {
            return service.getCustomerByName(name)
                    .map(c -> ResponseEntity.ok(CustomerResponse.of(c, service.calculateTier(c))))
                    .orElseThrow(() -> new com.example.customer_api.exception.NotFoundException("Customer not found"));
        }

        if (email != null) {
            return service.getCustomerByEmail(email)
                    .map(c -> ResponseEntity.ok(CustomerResponse.of(c, service.calculateTier(c))))
                    .orElseThrow(() -> new com.example.customer_api.exception.NotFoundException("Customer not found"));
        }

        return ResponseEntity.badRequest().body("Provide name or email as query param.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @RequestBody @Valid Customer customer) {
        Customer updated = service.updateCustomer(id, customer);
        return ResponseEntity.ok(CustomerResponse.of(updated, service.calculateTier(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
