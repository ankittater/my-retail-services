package com.ankit.myRetail.dto;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;

@Introspected
public class Product {

    @NotNull
    private Long id;

    private String name;

    @NotNull
    CurrentPrice current_price;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(Long id, String name, CurrentPrice current_price) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentPrice getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(CurrentPrice current_price) {
        this.current_price = current_price;
    }
}
