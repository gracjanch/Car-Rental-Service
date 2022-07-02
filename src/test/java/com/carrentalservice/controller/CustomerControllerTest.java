package com.carrentalservice.controller;

import com.carrentalservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllCustomersListReturnEmptyListWhenNoCustomerAdded() throws Exception {
        Mockito.when((customerService.getAll()))
                .thenReturn(List.of());

        mockMvc.perform((MockMvcRequestBuilders.get("/customers")))
                .andDo(print())
                .andExpect(content().string(containsString("[]")))
                .andExpect(status().is(200));
    }

}