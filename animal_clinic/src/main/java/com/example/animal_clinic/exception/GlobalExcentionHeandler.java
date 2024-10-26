package com.example.animal_clinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcentionHeandler {
    @ExceptionHandler(ClinicNotFoundException.class)
    public ResponseEntity<String> handleClinicNotFoundException(ClinicNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Возвращаем сообщение об ошибке и статус 404
    }

    // Обрабатываем исключения валидации данных и возвращаем 400 статус с сообщением об ошибке
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        // Получаем сообщение из валидации (например, @NotBlank)
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // Возвращаем сообщение об ошибке валидации и статус 400
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        // Возвращаем сообщение об ошибке и статус 400 Bad Request
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Обрабатываем любые другие исключения (например, внутренние ошибки) и возвращаем 500 статус
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Произошла ошибка на сервере. Попробуйте позже.", HttpStatus.INTERNAL_SERVER_ERROR);
        // Возвращаем сообщение об общей ошибке и статус 500 (внутренняя ошибка сервера)
    }
}