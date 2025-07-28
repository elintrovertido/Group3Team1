package com.ecommerce.notificationservice.exception;

import com.ecommerce.notificationservice.dto.ErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found error");
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.handleResourceNotFound(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource not found error", error.getMessage());
    }

    @Test
    void testHandleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.handleIllegalArgument(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Invalid argument", error.getMessage());
    }

    @Test
    void testHandleGlobalException() {
        Exception ex = new Exception("Something went wrong");
        ResponseEntity<ErrorDTO> response = globalExceptionHandler.handleGlobalException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatus());
        assertEquals("An unexpected error occurred: Something went wrong", error.getMessage());
    }
}
