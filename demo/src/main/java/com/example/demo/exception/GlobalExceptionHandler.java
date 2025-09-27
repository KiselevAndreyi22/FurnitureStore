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

    @ExceptionHandler(UknownFileFormatException.class)
    public ResponseEntity<Object> handleUknownFileFormatException(UknownFileFormatException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Имя пользователя или email не найдены!",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(ProductInCartNotFoundException.class)
    public ResponseEntity<Object> handleProductInCartNotFoundException(ProductInCartNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Данного продукта нет в корзине!",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFound(IdNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ID не найден",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(UploadFileIsEmptyException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileIsEmpty(UploadFileIsEmptyException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Файл пуст",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(InvalidVerifyCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidVerifyCode(InvalidVerifyCodeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Неверный код подтверждения",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(AttemptsExceededException.class)
    public ResponseEntity<ErrorResponse> handleAttemptsExceededException(AttemptsExceededException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Закончились попытки для подтверждения",
                HttpStatus.UNAUTHORIZED
        );
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
