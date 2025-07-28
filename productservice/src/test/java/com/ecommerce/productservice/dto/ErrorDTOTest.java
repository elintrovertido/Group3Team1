package com.ecommerce.productservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorDTOTest {

    @Test
    public void testConstructorAndGetters() {
        int status = 404;
        String message = "Not Found";

        ErrorDTO errorDTO = new ErrorDTO(status, message);

        assertEquals(status, errorDTO.getStatus());
        assertEquals(message, errorDTO.getMessage());
    }

    @Test
    public void testSetters() {
        ErrorDTO errorDTO = new ErrorDTO(0, null);

        int newStatus = 500;
        String newMessage = "Internal Server Error";

        errorDTO.setStatus(newStatus);
        errorDTO.setMessage(newMessage);

        assertEquals(newStatus, errorDTO.getStatus());
        assertEquals(newMessage, errorDTO.getMessage());
    }
}