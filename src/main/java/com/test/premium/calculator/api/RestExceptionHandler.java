package com.test.premium.calculator.api;

import com.test.premium.calculator.exceptions.PremiumCalculatorAppException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PremiumCalculatorAppException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorClass> handlePremiumCalculatorAppException(PremiumCalculatorAppException ex) {
        return new ResponseEntity<>(new ErrorClass(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    public static class ErrorClass {
        private String message;
    }

}
