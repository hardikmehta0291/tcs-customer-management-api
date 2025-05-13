package com.example.customer_api;

import com.example.customer_api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import com.example.customer_api.exception.NotFoundException;
import com.example.customer_api.model.Customer;
import com.example.customer_api.service.CustomerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class CustomerApiApplicationTests {

	@Mock
	private CustomerRepository repository;

	@InjectMocks
	private CustomerService service;

	private Customer customer;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		customer = Customer.builder()
				.id(UUID.randomUUID())
				.name("Hardik Mehta")
				.email("mehtahardik0291@gmail.com")
				.annualSpend(new BigDecimal("12000"))
				.lastPurchaseDate(LocalDateTime.now().minusMonths(3))
				.build();
	}

	@Test
	void testCreateCustomer() {
		when(repository.save(any())).thenReturn(customer);
		Customer created = service.createCustomer(customer);
		assertEquals(customer.getEmail(), created.getEmail());
	}

	@Test
	void testGetCustomerById_Success() {
		when(repository.findById(customer.getId())).thenReturn(Optional.of(customer));
		Customer found = service.getCustomerById(customer.getId());
		assertEquals(customer.getName(), found.getName());
	}

	@Test
	void testGetCustomerById_NotFound() {
		UUID id = UUID.randomUUID();
		when(repository.findById(id)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> service.getCustomerById(id));
	}

	@Test
	void testCalculateTier_Platinum() {
		customer.setAnnualSpend(new BigDecimal("15000"));
		customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(2));
		String tier = service.calculateTier(customer);
		assertEquals("Platinum", tier);
	}

	@Test
	void testCalculateTier_Gold() {
		customer.setAnnualSpend(new BigDecimal("2000"));
		customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(6));
		String tier = service.calculateTier(customer);
		assertEquals("Gold", tier);
	}

	@Test
	void testCalculateTier_SilverBySpend() {
		customer.setAnnualSpend(new BigDecimal("500"));
		String tier = service.calculateTier(customer);
		assertEquals("Silver", tier);
	}

	@Test
	void testCalculateTier_SilverByDate() {
		customer.setAnnualSpend(new BigDecimal("5000"));
		customer.setLastPurchaseDate(LocalDateTime.now().minusYears(2));
		String tier = service.calculateTier(customer);
		assertEquals("Silver", tier);
	}
}

