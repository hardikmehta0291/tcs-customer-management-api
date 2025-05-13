package com.example.customer_api.service;

import com.example.customer_api.exception.NotFoundException;
import com.example.customer_api.model.Customer;
import com.example.customer_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer createCustomer(Customer customer) {
        customer.setId(null); // ID will be auto-generated
        return repository.save(customer);
    }

    public Customer getCustomerById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public Optional<Customer> getCustomerByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Customer updateCustomer(UUID id, Customer input) {
        Customer customer = getCustomerById(id);
        customer.setName(input.getName());
        customer.setEmail(input.getEmail());
        customer.setAnnualSpend(input.getAnnualSpend());
        customer.setLastPurchaseDate(input.getLastPurchaseDate());
        return repository.save(customer);
    }

    public void deleteCustomer(UUID id) {
        repository.deleteById(id);
    }

    public String calculateTier(Customer customer) {
        BigDecimal spend = customer.getAnnualSpend();
        LocalDateTime lastPurchase = customer.getLastPurchaseDate();
        LocalDateTime now = LocalDateTime.now();

        if (spend == null || spend.compareTo(BigDecimal.ZERO) <= 0) {
            return "Silver";
        }

        if (spend.compareTo(new BigDecimal("10000")) >= 0 && lastPurchase != null &&
                lastPurchase.isAfter(now.minusMonths(6))) {
            return "Platinum";
        }

        if (spend.compareTo(new BigDecimal("1000")) >= 0 && spend.compareTo(new BigDecimal("10000")) < 0 &&
                lastPurchase != null && lastPurchase.isAfter(now.minusMonths(12))) {
            return "Gold";
        }

        return "Silver";
    }
}