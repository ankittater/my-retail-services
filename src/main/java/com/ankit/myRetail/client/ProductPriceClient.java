package com.ankit.myRetail.client;

import com.ankit.myRetail.configuration.ProductPriceClientConfiguration;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import com.ankit.myRetail.model.ProductPrice;

@Client(value = "${productPrice.endpoint}", configuration = ProductPriceClientConfiguration.class)
public interface ProductPriceClient {

    @Get("/products/{id}/price")
    ProductPrice fetchProductPrice(long id);

}
