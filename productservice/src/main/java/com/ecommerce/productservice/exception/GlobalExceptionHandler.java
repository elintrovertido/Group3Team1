package com.ecommerce.productservice.exception;


import com.ecommerce.productservice.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> productNotFound(ProductNotFoundException ex){
        return new ResponseEntity<>(new ErrorDTO(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<ErrorDTO> productNotAvailable(ProductNotAvailableException ex){
        return new ResponseEntity<>(new ErrorDTO(400, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exception(Exception ex){
        return new ResponseEntity<>(new ErrorDTO(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
