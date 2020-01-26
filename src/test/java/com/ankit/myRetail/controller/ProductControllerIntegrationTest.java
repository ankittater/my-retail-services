package com.ankit.myRetail.controller;

import com.ankit.myRetail.dto.CurrentPrice;
import com.ankit.myRetail.dto.Product;
import com.ankit.myRetail.model.ProductPrice;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ProductControllerIntegrationTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        System.out.println(server.getURL());
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Test
    public void testProductController() {
        long id = 13860428;
        final Product product = client.toBlocking().retrieve(HttpRequest.GET("/products/" + id), Product.class);
        assertNotNull(product);
        product.setCurrent_price(new CurrentPrice(200.0, "INR"));
        final ProductPrice addedProductPrice = client.toBlocking().retrieve(HttpRequest.POST("/products", product), ProductPrice.class);
        assertNotNull(product);
        assertEquals(product.getCurrent_price().getValue(), addedProductPrice.getValue());
        product.setCurrent_price(new CurrentPrice(500.0, "INR"));
        HttpResponse response = client.toBlocking().exchange(HttpRequest.PUT("/products", product));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        final Product productWithUpdatedPrice = client.toBlocking().retrieve(HttpRequest.GET("/products/" + id), Product.class);
        assertNotNull(product);
        assertEquals(product.getCurrent_price().getValue(), productWithUpdatedPrice.getCurrent_price().getValue());
    }
}
