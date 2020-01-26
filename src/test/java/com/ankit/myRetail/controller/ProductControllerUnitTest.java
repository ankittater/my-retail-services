package com.ankit.myRetail.controller;

import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import com.ankit.myRetail.model.ProductPrice;
import com.ankit.myRetail.service.ProductPriceService;
import com.ankit.myRetail.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@MicronautTest
class ProductControllerUnitTest {

    @Inject
    ProductService productService;

    @Inject
    ProductPriceService productPriceService;


    @Inject
    @Client("/")
    HttpClient client;


    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void getProduct(long id) {
        when(productService.getProductDetails(id))
                .then(invocation -> getProduct());
        final Product product = client.toBlocking().retrieve(HttpRequest.GET("/products/" + id), Product.class);

        assertEquals(
                "test-product",
                product.getName()
        );
        verify(productService).getProductDetails(id);
    }

    @Test
    void save() {
        Assertions.assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.POST("/products", new Product())));
        Product product = getProduct();
        ProductPrice productPrice = new ProductPrice(product.getId(), product.getCurrent_price().getValue(), product.getCurrent_price().getCurrency_code());

        when(productPriceService.save(any(Product.class))).thenReturn(productPrice);

        ProductPrice savedProductPrice = client.toBlocking().retrieve(HttpRequest.POST("/products", product), ProductPrice.class);

        assertEquals(
                savedProductPrice.getCurrency_code()
                ,
                savedProductPrice.getCurrency_code()
        );
        verify(productPriceService).save(any(Product.class));

    }

    @Test
    void update() {
        Assertions.assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.PUT("/products", new Product())));
        Product product = getProduct();
        when(productPriceService.update(any(Product.class))).thenReturn(1);
        HttpResponse response = client.toBlocking().exchange(HttpRequest.PUT("/products", product));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        verify(productPriceService).update(any(Product.class));
    }

    @ParameterizedTest()
    @ValueSource(longs = {10, 25})
    void getPrice(long id) {
        ProductPrice productPrice = new ProductPrice(id, (double) (id * 10), "USD");
        when(productPriceService.get(id)).thenReturn(Optional.of(productPrice));
        ProductPrice price = client.toBlocking().retrieve(HttpRequest.GET("/products/" + id + "/price"), ProductPrice.class);
        assertEquals(productPrice.getValue(), price.getValue());
        assertNotNull(price);
        verify(productPriceService).get(id);
    }

    @MockBean(ProductService.class)
    ProductService productService() {
        return mock(ProductService.class);
    }

    @MockBean(ProductPriceService.class)
    ProductPriceService productPriceService() {
        return mock(ProductPriceService.class);
    }

    private Product getProduct() {
        return new Product(10L, "test-product", new CurrentPrice(10.0, "USD"));
    }


}