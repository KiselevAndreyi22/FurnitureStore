package com.example.demo.exception;

import com.example.demo.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotExistException(ProductNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Товар не найден!",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotExistException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Имя пользователя или email не найдены!",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
