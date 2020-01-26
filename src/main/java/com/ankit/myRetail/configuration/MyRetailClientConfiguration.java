package com.ankit.myRetail.configuration;


import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;

import java.net.URI;

@ConfigurationProperties(MyRetailClientConfiguration.PREFIX)
@Requires(property = MyRetailClientConfiguration.PREFIX)
public class MyRetailClientConfiguration extends HttpClientConfiguration {

    static final String PREFIX = "myRetail";


    private URI endpoint;

    private String version;

    /**
     * HTTP client connection pool configuration.
     */
    private final MyRetailClientPoolConfiguration connectionPoolConfiguration;

    public MyRetailClientConfiguration(
            final ApplicationConfiguration applicationConfiguration,
            final MyRetailClientPoolConfiguration connectionPoolConfiguration) {
        super(applicationConfiguration);
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }

    @Override
    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return connectionPoolConfiguration;
    }

    @ConfigurationProperties(ConnectionPoolConfiguration.PREFIX)
    public static class MyRetailClientPoolConfiguration extends ConnectionPoolConfiguration {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
