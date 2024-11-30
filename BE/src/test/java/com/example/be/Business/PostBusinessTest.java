package com.example.be.Business;

import com.example.backend.Business.exception.ProductAlreadyExistsException;
import com.example.backend.Business.impl.productUseCasesImpl.*;
import com.example.backend.Domain.Product.*;
import com.example.backend.Repository.ProductRepository;
import com.example.backend.Repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostBusinessTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    @InjectMocks
    private GetProductsUseCaseImpl getProductsUseCase;

    @InjectMocks
    private GetProductUseCaseImpl getProductUseCase;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    @InjectMocks
    private GetProductsFilteredUseCaseImpl getProductsFilteredUseCase;

    @Test
    public void testCreateProduct_Success() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest();
        request.setTitle("New Product");
        request.setPrice(19.99);
        request.setDescription("This is a test product.");

        ProductEntity savedProduct = new ProductEntity();
        savedProduct.setId(1); // Assuming the ID type is Integer
        savedProduct.setTitle("New Product");
        savedProduct.setPrice(19.99);
        savedProduct.setDescription("This is a test product.");

        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedProduct);

        // Act
        CreateProductResponse response = createProductUseCase.createProduct(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getProductId());
        verify(productRepository).save(any(ProductEntity.class));
    }

    @Test
    public void testDeleteProduct_Success() {
        // Arrange
        long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        deleteProductUseCase.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testGetProducts_Success() {
        // Arrange
        GetAllProductsRequest request = new GetAllProductsRequest();

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(1);
        productEntity1.setTitle("Product 1");
        productEntity1.setPrice(9.99);
        productEntity1.setDescription("Description 1");

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(2);
        productEntity2.setTitle("Product 2");
        productEntity2.setPrice(19.99);
        productEntity2.setDescription("Description 2");

        List<ProductEntity> productEntities = Arrays.asList(productEntity1, productEntity2);
        when(productRepository.findAll()).thenReturn(productEntities);

        // Actp
        GetAllProductsResponse response = getProductsUseCase.getProducts(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(2, response.getProducts().size());

        Product product1 = response.getProducts().get(0);
        assertEquals(1, product1.getId());
        assertEquals("Product 1", product1.getTitle());
        assertEquals(9.99, product1.getPrice());
        assertEquals("Description 1", product1.getDescription());

        Product product2 = response.getProducts().get(1);
        assertEquals(2, product2.getId());
        assertEquals("Product 2", product2.getTitle());
        assertEquals(19.99, product2.getPrice());
        assertEquals("Description 2", product2.getDescription());
    }

    @Test
    public void testGetProduct_Success() {
        // Arrange
        int productId = 1;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setTitle("Test Product");
        productEntity.setPrice(29.99);
        productEntity.setDescription("Test Description");

        when(productRepository.findById((long)productId)).thenReturn(Optional.of(productEntity));

        // Act
        Optional<Product> productOptional = getProductUseCase.getProduct(productId);

        // Assert
        assertTrue(productOptional.isPresent());
        Product product = productOptional.get();
        assertEquals(productId, product.getId());
        assertEquals("Test Product", product.getTitle());
        assertEquals(29.99, product.getPrice());
        assertEquals("Test Description", product.getDescription());
    }

    @Test
    public void testUpdateProduct_Success() {
        // Arrange
        long productId = 1L;
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(productId);
        request.setTitle("Updated Title");
        request.setPrice(39.99);
        request.setDescription("Updated Description");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId((int)productId);
        productEntity.setTitle("Original Title");
        productEntity.setPrice(29.99);
        productEntity.setDescription("Original Description");

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Act
        updateProductUseCase.updateProduct(request);

        // Assert
        verify(productRepository).save(productEntity);
        verify(productRepository).findById(productId);
    }

    @Test
    public void testUpdateProduct_NotFound() {
        // Arrange
        long productId = 2L;
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(productId);
        request.setTitle("Non-existent Product");
        request.setPrice(19.99);
        request.setDescription("This product does not exist.");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductAlreadyExistsException.class, () -> {
            updateProductUseCase.updateProduct(request);
        });
    }

    @Test
    void getProductsFiltered_WhenSortIsAscending_ShouldReturnProductsInAscendingOrder() {
        // Arrange
        GetAllProductsFilteredRequest request = GetAllProductsFilteredRequest.builder()
                .minPrice(new BigDecimal("10.00"))
                .sort("ASC")
                .build();
        List<ProductEntity> mockedResult = Arrays.asList(
                ProductEntity.builder().id(1).title("Product A").price(10.00).description("Description A").build(),
                ProductEntity.builder().id(2).title("Product B").price(15.00).description("Description B").build()
        );

        when(productRepository.findProductsPricedAboveWithAscending(request.getMinPrice())).thenReturn(mockedResult);

        // Act
        GetAllProductsFilteredResponse response = getProductsFilteredUseCase.getProductsFiltered(request);

        // Assert
        assertEquals(2, response.getProducts().size());
        assertEquals(10.00, response.getProducts().get(0).getPrice(), 0.01);
        assertEquals(15.00, response.getProducts().get(1).getPrice(), 0.01);
    }

    @Test
    void getProductsFiltered_WhenSortIsDescending_ShouldReturnProductsInDescendingOrder() {
        // Arrange
        GetAllProductsFilteredRequest request = GetAllProductsFilteredRequest.builder()
                .minPrice(new BigDecimal("10.00"))
                .sort("DESC")
                .build();
        List<ProductEntity> mockedResult = Arrays.asList(
                ProductEntity.builder().id(2).title("Product B").price(15.00).description("Description B").build(),
                ProductEntity.builder().id(1).title("Product A").price(10.00).description("Description A").build()
        );

        when(productRepository.findProductsPricedAboveWithDescending(request.getMinPrice())).thenReturn(mockedResult);

        // Act
        GetAllProductsFilteredResponse response = getProductsFilteredUseCase.getProductsFiltered(request);

        // Assert
        assertEquals(2, response.getProducts().size());
        assertEquals(15.00, response.getProducts().get(0).getPrice(), 0.01);
        assertEquals(10.00, response.getProducts().get(1).getPrice(), 0.01);
    }

    @Test
    void getProductsFiltered_InvalidSortDirection_ThrowsIllegalArgumentException() {
        // Arrange
        GetAllProductsFilteredRequest request = new GetAllProductsFilteredRequest();
        request.setSort("INVALID");
        request.setMinPrice(BigDecimal.valueOf(100));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> getProductsFilteredUseCase.getProductsFiltered(request));
        verify(productRepository, never()).findProductsPricedAboveWithAscending(any(BigDecimal.class));
        verify(productRepository, never()).findProductsPricedAboveWithDescending(any(BigDecimal.class));
    }
}
