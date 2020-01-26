package com.ankit.myRetail.service;


import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import com.ankit.myRetail.exception.MyRetailException;
import com.ankit.myRetail.respository.ProductPriceRepository;
import com.ankit.myRetail.respository.ProductPriceRepositoryImpl;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import com.ankit.myRetail.model.ProductPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import javax.inject.Inject;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class ProductPriceServiceTest {

    @Inject
    private ProductPriceService productPriceService;

    @Inject
    ProductPriceRepository productPriceRepository;


    @MockBean(ProductPriceRepositoryImpl.class)
    ProductPriceRepository productPriceRepository() {
        return mock(ProductPriceRepository.class);
    }


    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void get(long id) {
        ProductPrice productPrice = getProductPrice(id);
        when(productPriceRepository.get(id)).then(invocationOnMock -> Optional.of(productPrice));
        Optional<ProductPrice> productPriceInstance = productPriceService.get(id);
        assertEquals(Optional.of(productPrice), productPriceInstance);
        verify(productPriceRepository).get(id);
    }

    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void getWithEmptyProduct(long id) {
        Optional<ProductPrice> productPriceInstance = productPriceService.get(id);
        assertFalse(productPriceInstance.isPresent());
        verify(productPriceRepository).get(id);
    }


    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void save(long id) {
        ProductPrice productPrice = getProductPrice(id);
        Product product = getProduct();
        product.setId(id);
        when(productPriceRepository.save(any(ProductPrice.class))).then(invocationOnMock -> productPrice);
        ProductPrice productPriceInstance = productPriceService.save(product);

        assertEquals(product.getId(), productPriceInstance.getId());

        verify(productPriceRepository).save(any(ProductPrice.class));
    }


    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void update(Long id) {
        ProductPrice productPrice = getProductPrice(id);
        Product product = getProduct();
        product.setId(id);

        when(productPriceRepository.get(id)).then(invocationOnMock -> Optional.of(productPrice));
        when(productPriceRepository.update(productPrice)).then(invocationOnMock -> 1);
        int rowUpdateCount = productPriceService.update(product);

        assertEquals(1, rowUpdateCount);

        verify(productPriceRepository).update(productPrice);
    }

    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void updateWithInvalidProduct(Long id) {
        ProductPrice productPrice = getProductPrice(id);
        Product product = getProduct();
        product.setId(id);

        when(productPriceRepository.get(id)).then(invocationOnMock -> Optional.empty());
        when(productPriceRepository.update(productPrice)).then(invocationOnMock -> 1);

        Assertions.assertThrows(MyRetailException.class, () -> productPriceService.update(product));

        verify(productPriceRepository).get(id);
        verify(productPriceRepository, never()).update(productPrice);
    }


    private ProductPrice getProductPrice(long id) {
        return new ProductPrice(id, id * 10.0, "USD");
    }

    private Product getProduct() {
        return new Product(10L, "test-product", new CurrentPrice(10.0, "USD"));
    }


}