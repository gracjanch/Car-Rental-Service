package com.carrentalservice.controller;

import com.carrentalservice.model.request.CarRequest;
import com.carrentalservice.model.request.CustomerRequest;
import com.carrentalservice.model.request.RentRequest;
import com.carrentalservice.repository.CarRepository;
import com.carrentalservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    @Order(1)
    void addRentFromValidRequestSaveEntityInDatabase() throws Exception {
        final CustomerRequest customerRequest = new CustomerRequest(
                "Name", "Lastname", "ID card", "ABC 123456");

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        final CarRequest carRequest = new CarRequest("TestB", "TestM", 5, "TE 1234",
                true, 2000, 20);

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        final RentRequest rentRequest = new RentRequest(LocalDateTime.now(),
                LocalDateTime.now().plusHours(4L), 500, 1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/rents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/rents"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].cost").value(500));
    }

    @Test
    @Order(2)
    void getRentByIdShouldReturnRentNotFoundException() throws Exception {
        final long rentId = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/rents/" + rentId))
                .andDo(print())
                .andExpect(content().string(containsString("Rent not found!")))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Order(3)
    void updateRentShouldUpdateEntity() throws Exception {
        final RentRequest rentUpdated = new RentRequest(LocalDateTime.now(),
                LocalDateTime.now().plusHours(5L), 550, 1L, 1L);
        final long rentId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put(
                "http://localhost:" + port + "/rents/" + rentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentUpdated)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                "http://localhost:" + port + "/rents/" + rentId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("cost").value(550));
    }

    @Test
    @Order(4)
    void addNewRentWhichContainRentedCarShouldReturnRentPeriodNotFree()
        throws Exception {
        final RentRequest rentRequest = new RentRequest(LocalDateTime.now().plusHours(2L),
                LocalDateTime.now().plusHours(8L), 600, 1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post(
                        "http://localhost:" + port + "/rents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andDo(print())
                .andExpect(content().string(containsString("Rent period is not free!")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(5)
    void deleteRentShouldDeleteEntity() throws Exception {
        final long rentId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(
                "http://localhost:" + port + "/rents/" + rentId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                "http://localhost:" + port + "/rents"))
                .andDo(print())
                .andExpect(content().string(containsString("[]")))
                .andExpect(status().is2xxSuccessful());
    }

}