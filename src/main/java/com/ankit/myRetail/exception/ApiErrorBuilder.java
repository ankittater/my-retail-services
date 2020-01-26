package com.ankit.myRetail.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.http.HttpStatus;

import java.util.Date;
import java.util.Map;

/**
 *  Builder Class to form ErrorMessage
 */
public class ApiErrorBuilder {

    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private String debugMessage;
    @JsonIgnore
    private HttpStatus status;
    private Map errors;

    public ApiErrorBuilder() {
        this.timestamp = new Date();
    }

    public ApiErrorBuilder(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDebugMessage() {
        return debugMessage;
    }


    public String getMessage() {
        return message;
    }

    public Map getErrors() {
        return errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApiErrorBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiErrorBuilder setErrors(Map errors) {
        this.errors = errors;
        return this;
    }

    public ApiErrorBuilder setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
        return this;
    }

    public ApiErrorBuilder setStatus(String debugMessage) {
        this.debugMessage = debugMessage;
        return this;
    }


}
