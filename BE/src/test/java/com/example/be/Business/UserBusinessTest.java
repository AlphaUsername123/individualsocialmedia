package com.example.be.Business;

import com.example.backend.Business.exception.InvalidCountryException;
import com.example.backend.Business.exception.InvalidCustomerException;
import com.example.backend.Business.impl.customerUseCasesImpl.*;
import com.example.backend.Domain.Customer.*;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.entity.CountryEntity;
import com.example.backend.Repository.entity.CustomerEntity;
import com.example.backend.Repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserBusinessTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CountryIdValidator countryIdValidator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateCustomerUseCaseImpl createCustomerUseCase;
    @InjectMocks
    private UpdateCustomerUseCaseImpl updateCustomerUseCase;
    @InjectMocks
    private GetCustomersUseCaseImpl getCustomersUseCase;
    @InjectMocks
    private GetCustomerUseCaseImpl getCustomerUseCase;
    @InjectMocks
    private DeleteCustomerUseCaseImpl deleteCustomerUseCase;


    @Test
    void createCustomer_ValidRequest_CreatesCustomer() {
        // Arrange
        CountryEntity countryEntity = CountryEntity.builder()
                .id(2L)
                .name("Netherlands")
                .code("NL")
                .build();

        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .name("John Doe")
                .countryId(2L)
                .password("password") // Assuming password is provided
                .build();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .country(countryEntity)
                .build();

        String encodedPassword = "encodedPassword"; // Mocked encoded password

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        // Act
        CreateCustomerResponse response = createCustomerUseCase.createCustomer(request);

        // Assert
        assertEquals(1L, response.getCustomerId());
        verify(countryIdValidator).validateId(request.getCountryId());
        verify(customerRepository).save(any(CustomerEntity.class));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createCustomer_InvalidCountryId_ThrowsInvalidCountryException() {
        // Arrange
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .name("John Doe")
                .countryId(1L)
                .build();

        doThrow(new InvalidCountryException()).when(countryIdValidator).validateId(1L);

        // Act & Assert
        assertThrows(InvalidCountryException.class, () -> createCustomerUseCase.createCustomer(request));
        verify(countryIdValidator).validateId(1L);
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_ValidRequest_UpdatesCustomer() {
        // Arrange
        CountryEntity countryEntity = CountryEntity.builder()
                .id(2L)
                .name("United States")
                .code("US")
                .build();

        UpdateCustomerRequest request = UpdateCustomerRequest.builder()
                .id(1L)
                .name("Updated Name")
                .countryId(2L)
                .build();

        CustomerEntity existingCustomer = CustomerEntity.builder()
                .id(1L)
                .name("Old Name")
                .country(countryEntity)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));

        // Act
        updateCustomerUseCase.updateCustomer(request);

        // Assert
        verify(customerRepository).findById(1L);
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_InvalidCustomerId_ThrowsException() {
        // Arrange
        UpdateCustomerRequest request = UpdateCustomerRequest.builder()
                .id(1L)
                .name("Updated Name")
                .countryId(2L)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCustomerException.class, () -> updateCustomerUseCase.updateCustomer(request));
        verify(customerRepository).findById(1L);
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void getCustomers_NoFilter_ReturnsAllCustomers() {
        // Arrange
        CountryEntity countryEntity = CountryEntity.builder()
                .id(1L)
                .name("United States")
                .code("US")
                .build();

        List<CustomerEntity> customerEntities = List.of(
                CustomerEntity.builder().id(1L).name("John Doe").country(countryEntity).build(),
                CustomerEntity.builder().id(2L).name("Jane Doe").country(countryEntity).build()
        );

        when(customerRepository.findAll()).thenReturn(customerEntities);

        // Act
        GetAllCustomersResponse response = getCustomersUseCase.getCustomers(new GetAllCustomersRequest());

        // Assert
        assertEquals(2, response.getCustomers().size());
        verify(customerRepository).findAll();
    }

    @Test
    void getCustomers_WithCountryCodeFilter_ReturnsFilteredCustomers() {
        // Arrange
        String countryCode = "US";
        GetAllCustomersRequest request = GetAllCustomersRequest.builder()
                .countryCode(countryCode)
                .build();

        List<CustomerEntity> customerEntities = List.of(
                CustomerEntity.builder()
                        .id(1L)
                        .name("John Doe")
                        .country(CountryEntity.builder()
                                .id(1L)
                                .name("United States")
                                .code(countryCode)
                                .build())
                        .build()
        );

        when(customerRepository.findByCountryCode(countryCode)).thenReturn(customerEntities);

        // Act
        GetAllCustomersResponse response = getCustomersUseCase.getCustomers(request);

        // Assert
        assertEquals(1, response.getCustomers().size());
        verify(customerRepository).findByCountryCode(countryCode);
    }

    @Test
    void getCustomer_ExistingCustomerId_ReturnsCustomer() {
        // Arrange
        CountryEntity countryEntity = CountryEntity.builder()
                .id(1L)
                .name("United States")
                .code("US")
                .build();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(1L)
                .name("John Doe")
                .country(countryEntity)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));

        // Act
        Optional<Customer> result = getCustomerUseCase.getCustomer(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(customerRepository).findById(1L);
    }

    @Test
    void getCustomer_NonExistingCustomerId_ReturnsEmpty() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = getCustomerUseCase.getCustomer(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(customerRepository).findById(1L);
    }

    @Test
    void deleteCustomer_ValidCustomerId_DeletesCustomer() {
        // Arrange
        long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // Act
        deleteCustomerUseCase.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomer_InvalidCustomerId_ThrowsException() {
        // Arrange
        long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCustomerException.class, () -> deleteCustomerUseCase.deleteCustomer(customerId));
        verify(customerRepository, never()).deleteById(customerId);
    }
}
