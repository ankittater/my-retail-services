package com.ankit.myRetail;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "My Retail Services",
                version = "1.0",
                description = "Services to fetches latest price",
                contact = @Contact( name = "Ankit Kumar Tater", email = "ankittater91@gmail.com")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}