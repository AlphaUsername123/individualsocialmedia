//package com.example.be.Configuration.db;
//
//import com.example.backend.Business.customerUseCases.CreateCustomerUseCase;
//import com.example.backend.Domain.Customer.CreateCustomerRequest;
//import com.example.backend.Repository.CountryRepository;
//import com.example.backend.Repository.ProductRepository;
//import com.example.backend.Repository.UserRepository;
//import com.example.backend.Repository.entity.*;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//@AllArgsConstructor
//public class DatabaseDummyDataInitializer {
//
//    private CountryRepository countryRepository;
//    private UserRepository userRepository;
//    private ProductRepository productRepository;
//    private PasswordEncoder passwordEncoder;
//    private CreateCustomerUseCase createCustomerUseCase;
//
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void populateDatabaseInitialDummyData() {
//        if (isDatabaseEmpty()) {
//            insertSomeCountries();
//            insertAdminUser();
//            insertCustomer();
//            insertProducts();
//        }
//    }
//
//    private boolean isDatabaseEmpty() {
//        return countryRepository.count() == 0;
//    }
//
//    private void insertAdminUser() {
//        UserEntity adminUser = UserEntity.builder()
//                .username("admin@fontys.nl")
//                .password(passwordEncoder.encode("test123"))
//                .build();
//        UserRoleEntity adminRole = UserRoleEntity.builder().role(RoleEnum.ADMIN).user(adminUser).build();
//        adminUser.setUserRoles(Set.of(adminRole));
//        userRepository.save(adminUser);
//    }
//
//    private void insertCustomer() {
//        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
//                .password("test123")
//                .name("Linda")
//                .countryId(1L)
//                .build();
//        createCustomerUseCase.createCustomer(createCustomerRequest);
//    }
//
//    private void insertProducts()
//    {
//        ProductEntity product = ProductEntity.builder()
//                .title("Milk")
//                .price(1.20)
//                .description("very good")
//                .build();
//
//        ProductEntity product1 = ProductEntity.builder()
//                .title("Bread")
//                .price(2.32)
//                .description("very good, very nice")
//                .build();
//
//        ProductEntity product2 = ProductEntity.builder()
//                .title("Chair")
//                .price(5.00)
//                .description("very good, very good")
//                .build();
//
//        productRepository.save(product);
//        productRepository.save(product1);
//        productRepository.save(product2);
//    }
//
//    private void insertSomeCountries() {
//        countryRepository.save(CountryEntity.builder().code("NL").name("Netherlands").build());
//        countryRepository.save(CountryEntity.builder().code("BG").name("Bulgaria").build());
//        countryRepository.save(CountryEntity.builder().code("RO").name("Romania").build());
//        countryRepository.save(CountryEntity.builder().code("BR").name("Brazil").build());
//        countryRepository.save(CountryEntity.builder().code("CN").name("China").build());
//    }
//}
