package com.herhackathon.challenge.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herhackathon.challenge.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(SecurityConfiguration.class)
class CustomerControllerTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnEmptyListOfCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfCustomers() throws Exception {
        List<Customer> customers = List.of(Customer.builder().id(1).firstName("John").lastName("Doe").build(),
                Customer.builder().id(2).firstName("Jane").lastName("Doe").build());
        when(customerRepository.findAll()).thenReturn(customers);
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(customers.size()));
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        Customer customer = Customer.builder().firstName("John").lastName("Doe").build();
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        Long id = 1L;
        doNothing().when(customerRepository).deleteById(id);
        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        Long id = 1L;
        Customer customer = Customer.builder().id(id).firstName("John").lastName("Doe").build();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }
}