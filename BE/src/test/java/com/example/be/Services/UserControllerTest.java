package com.example.be.Services;


import com.example.backend.Business.customerUseCases.*;
import com.example.backend.Domain.Country;
import com.example.backend.Domain.Customer.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCustomerUseCase getCustomerUseCase;
    @MockBean
    private GetCustomersUseCase getCustomersUseCase;
    @MockBean
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;
    @MockBean
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"CUSTOMER"})
    void getCustomer_shouldReturn200WithCustomer_whenCustomerFound() throws Exception {
        Customer customer = Customer.builder()
                .country(getBrazilDTO())
                .name("Rivaldo Vítor Borba Ferreira")
                .id(10L)
                .build();
        when(getCustomerUseCase.getCustomer(10L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                           {"id":10, "name":"Rivaldo Vítor Borba Ferreira","country":{"id":1,"code":"BR","name":"Brazil"}}
                        """));

        verify(getCustomerUseCase).getCustomer(10L);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"CUSTOMER"})
    void getCustomer_shouldReturn404Error_whenCustomerNotFound() throws Exception {
        when(getCustomerUseCase.getCustomer(10L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/10"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getCustomerUseCase).getCustomer(10L);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void getAllCustomers_shouldReturn200WithCustomersList_WhenNoFilterProvided() throws Exception {
        GetAllCustomersResponse responseDTO = GetAllCustomersResponse.builder()
                .customers(List.of(
                        Customer.builder().id(1L).name("Romario").country(getBrazilDTO()).build(),
                        Customer.builder().id(1L).name("Ronaldo").country(getBrazilDTO()).build()
                ))
                .build();
        GetAllCustomersRequest request = GetAllCustomersRequest.builder().build();
        when(getCustomersUseCase.getCustomers(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "customers":[
                                    {"id":1, "name":"Romario","country":{"id":1,"code":"BR","name":"Brazil"}},
                                    {"id":1, "name":"Ronaldo","country":{"id":1,"code":"BR","name":"Brazil"}}
                                ]
                            }
                        """));

        verify(getCustomersUseCase).getCustomers(request);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void getAllCustomers_shouldReturn200WithCustomersList_WhenCountryFilterProvided() throws Exception {
        Country country = Country.builder()
                .code("NL")
                .name("Netherlands")
                .id(2L)
                .build();
        GetAllCustomersResponse responseDTO = GetAllCustomersResponse.builder()
                .customers(List.of(
                        Customer.builder().id(1L).name("Dennis Bergkamp").country(country).build(),
                        Customer.builder().id(1L).name("Johan Cruyff").country(country).build()
                ))
                .build();
        GetAllCustomersRequest request = GetAllCustomersRequest.builder()
                .countryCode("NL")
                .build();
        when(getCustomersUseCase.getCustomers(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/customers").param("country", "NL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "customers":[
                                    {"id":1,"name":"Dennis Bergkamp","country":{"id":2,"code":"NL","name":"Netherlands"}},
                                    {"id":1,"name":"Johan Cruyff","country":{"id":2,"code":"NL","name":"Netherlands"}}
                                ]
                            }
                        """));

        verify(getCustomersUseCase).getCustomers(request);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void deleteCustomer_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/customers/100"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteCustomerUseCase).deleteCustomer(100L);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void createCustomer_shouldReturn201_whenRequestIsValid() throws Exception {
        CreateCustomerRequest expectedRequest = CreateCustomerRequest.builder()
                .password("test123")
                .name("James")
                .countryId(1L)
                .build();
        when(createCustomerUseCase.createCustomer(expectedRequest))
                .thenReturn(CreateCustomerResponse.builder()
                        .customerId(200L)
                        .build());

        mockMvc.perform(post("/customers")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "James",
                                    "password": "test123",
                                    "countryId": "1"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            { "customerId":  200 }
                        """));

        verify(createCustomerUseCase).createCustomer(expectedRequest);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"CUSTOMER"})
    void updateCustomer_shouldReturn204() throws Exception {
        mockMvc.perform(put("/customers/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "James",
                                    "countryId": "1"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        UpdateCustomerRequest expectedRequest = UpdateCustomerRequest.builder()
                .id(100L)
                .name("James")
                .countryId(1L)
                .build();
        verify(updateCustomerUseCase).updateCustomer(expectedRequest);
    }

    private Country getBrazilDTO() {
        return Country.builder()
                .id(1L)
                .code("BR")
                .name("Brazil")
                .build();
    }
}