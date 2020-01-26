package com.ankit.myRetail.model;


import javax.persistence.*;

/**
 * Entity class to persist Product Price and Code into DB
 */
@Entity
@Table(name = "product_price")
public class ProductPrice {

    @Id
    private Long id;

    @Column
    private Double value;

    @Column
    private String currency_code;

    public ProductPrice(){

    }

    public ProductPrice(Long id, Double value, String currency_code) {
        this.id = id;
        this.value = value;
        this.currency_code = currency_code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
