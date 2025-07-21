package com.ecommerce.productservice.exception;

import com.ecommerce.productservice.model.ErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testProductNotFoundException() {
        String errorMessage = "Product not found";
        ProductNotFoundException ex = new ProductNotFoundException(errorMessage);
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.productNotFound(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void testProductNotAvailableException() {
        String errorMessage = "Product not available";
        ProductNotAvailableException ex = new ProductNotAvailableException(errorMessage);
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.productNotAvailable(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void testGenericException() {
        String errorMessage = "Generic error";
        Exception ex = new Exception(errorMessage);
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.exception(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

}