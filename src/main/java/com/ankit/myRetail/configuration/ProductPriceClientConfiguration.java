package com.ankit.myRetail.configuration;


import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;

import java.net.URI;

@ConfigurationProperties(ProductPriceClientConfiguration.PREFIX)
@Requires(property = ProductPriceClientConfiguration.PREFIX)
public class ProductPriceClientConfiguration extends HttpClientConfiguration {

     static final String PREFIX = "productPrice";


    private URI endpoint;

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * HTTP client connection pool configuration.
     */
    private final ProductPriceClientPoolConfiguration connectionPoolConfiguration;

    public ProductPriceClientConfiguration(
            final ApplicationConfiguration applicationConfiguration,
            final ProductPriceClientPoolConfiguration connectionPoolConfiguration) {
        super(applicationConfiguration);
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }

    @ConfigurationProperties(ConnectionPoolConfiguration.PREFIX)
    public static class ProductPriceClientPoolConfiguration extends ConnectionPoolConfiguration {
    }

    @Override
    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return connectionPoolConfiguration;
    }
}
