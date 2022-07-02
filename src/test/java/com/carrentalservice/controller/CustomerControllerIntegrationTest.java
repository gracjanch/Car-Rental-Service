package com.carrentalservice.controller;

import com.carrentalservice.model.request.CustomerRequest;
import com.carrentalservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void cleanDb() {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    void addCustomerFromValidRequestSaveEntityInDatabase() throws Exception {
        final CustomerRequest customerRequest = new CustomerRequest(
                "Name", "Lastname", "ID card", "ABC 123456");

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/customers"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Name"));
    }

    @Test
    @Order(2)
    void getCustomerByIdShouldReturnCustomerNotFoundException() throws Exception {
        final long customerId = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/customers/" + customerId))
                .andDo(print())
                .andExpect(content().string(containsString("Customer not found!")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(3)
    void updateCustomerShouldUpdateEntity() throws Exception {
        final CustomerRequest customerRequest = new CustomerRequest(
                "Name", "Lastname", "ID card", "ABC 123456");
        final CustomerRequest customerUpdated = new CustomerRequest(
                "Updated name", "Lastname", "ID card", "ABC 123456");

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.put(
                                "http://localhost:" + port + "/customers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerUpdated)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/customers/2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name").value("Updated name"))
                .andExpect(jsonPath("lastName").value("Lastname"));
    }

    @Test
    @Order(4)
    void deleteCustomerShouldDeleteEntity() throws Exception {
        final CustomerRequest customerRequest = new CustomerRequest(
                "Name", "Lastname", "ID card", "ABC 123456");
        final long customerId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                        "http://localhost:" + port + "/customers/" + customerId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/customers"))
                .andDo(print())
                .andExpect(content().string(containsString("[]")))
                .andExpect(status().is2xxSuccessful());
    }

}