package com.ankit.myRetail.exception;


import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;

import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;

import java.util.Map;

import static com.ankit.myRetail.configuration.GlobalConstant.INTERNAL_SERVER_ERROR_MESSAGE;

/**
 * Handle any runtime exception thrown from any Controller,and form a Custom HttpResponse
 */
@Produces
@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class MyRetailExceptionHandler implements ExceptionHandler<RuntimeException, HttpResponse> {

    /**
     *
     * @param request HttpRequest forwarded from controller
     * @param exception any runtime exception
     * @return Custom HttpResponse based exception
     */
    @Override
    public HttpResponse handle(HttpRequest request, RuntimeException exception) {
        if (exception instanceof MyRetailException) {
            MyRetailException myRetailException = (MyRetailException) exception;
            return HttpResponse.status((myRetailException.getApiErrorBuilder().getStatus())).body(myRetailException.getApiErrorBuilder());
        } else if (exception instanceof ConstraintViolationException) {
            ApiErrorBuilder errorBuilder = new ApiErrorBuilder().setDebugMessage(exception.getMessage()).setErrors((Map) ((ConstraintViolationException) exception).getConstraintViolations());
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(errorBuilder);
        } else {
            return HttpResponse.serverError().body(new ApiErrorBuilder().setDebugMessage(exception.getMessage()).setMessage(INTERNAL_SERVER_ERROR_MESSAGE));
        }

    }
}