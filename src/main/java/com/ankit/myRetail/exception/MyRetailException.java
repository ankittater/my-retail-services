package com.ankit.myRetail.exception;


/**
 * Custom Global Exception which can be thrown from anywhere in application
 */
public class MyRetailException extends RuntimeException {

    private ApiErrorBuilder apiErrorBuilder;

    public MyRetailException(ApiErrorBuilder apiErrorBuilder) {
        this.apiErrorBuilder=apiErrorBuilder;
    }

    public ApiErrorBuilder getApiErrorBuilder() {
        return apiErrorBuilder;
    }

    public void setApiErrorBuilder(ApiErrorBuilder apiErrorBuilder) {
        this.apiErrorBuilder = apiErrorBuilder;
    }
}
