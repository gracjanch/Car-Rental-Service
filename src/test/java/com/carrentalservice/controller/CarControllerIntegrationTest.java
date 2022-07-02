package com.carrentalservice.controller;

import com.carrentalservice.model.request.CarRequest;
import com.carrentalservice.repository.CarRepository;
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
class CarControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CarRepository carRepository;

    @BeforeEach
    void cleanDb() {
        carRepository.deleteAll();
    }

    @Test
    @Order(1)
    void addCarFromValidRequestSaveEntityInDatabase() throws Exception {
        final CarRequest carRequest = new CarRequest("TestB", "TestM", 5, "TE 1234",
                true, 2000, 20);

        mockMvc.perform(MockMvcRequestBuilders.post(
                "http://localhost:" + port + "/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                "http://localhost:" + port + "/cars"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].brand").value("TestB"));

    }

    @Test
    @Order(2)
    void getCarByIdShouldReturnCarNotFoundException() throws Exception {
        final long carId = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/cars/" + carId))
                .andDo(print())
                .andExpect(content().string(containsString("Car not found!")))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Order(3)
    void updateCarShouldUpdateEntity() throws Exception {
        final CarRequest carRequest = new CarRequest("TestB", "TestM", 5, "TE 1234",
                true, 2000, 20);
        final CarRequest carUpdated = new CarRequest("TestB", "Updated model", 5, "TE 1234",
                true, 2000, 20);

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.put(
                                "http://localhost:" + port + "/cars/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carUpdated)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                       "http://localhost:" + port + "/cars/2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("model").value("Updated model"))
                .andExpect(jsonPath("brand").value("TestB"));
    }

    @Test
    @Order(4)
    void deleteCarShouldDeleteEntity() throws Exception {
        final CarRequest carRequest = new CarRequest("TestB", "TestM", 5, "TE 1234",
                true, 2000, 20);
        final long carId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.post(
                                "http://localhost:" + port + "/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                        "http://localhost:" + port + "/cars/" + carId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:" + port + "/cars"))
                .andDo(print())
                .andExpect(content().string(containsString("[]")))
                .andExpect(status().is2xxSuccessful());

    }
}