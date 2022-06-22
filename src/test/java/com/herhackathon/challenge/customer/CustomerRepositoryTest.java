package com.herhackathon.challenge.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldStoreCustomer() {
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer.getId());
        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());
    }

    @Test
    void shouldFindAllCustomers() {
        List<Customer> customers = List.of(Customer.builder().firstName("John").lastName("Doe").build(),
                Customer.builder().firstName("Jane").lastName("Doe").build());
        customers.forEach(c -> entityManager.persistAndFlush(c));
        entityManager.clear();

        List<Customer> allCustomers = customerRepository.findAll();

        assertEquals(customers.size(), allCustomers.size());
    }
}