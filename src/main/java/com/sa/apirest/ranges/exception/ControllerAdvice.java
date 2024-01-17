package com.sa.apirest.ranges.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice

public class ControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDTO> businessExceptionHandler(BusinessException e){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status_code(String.valueOf((e.getHttpStatus().value())))
                .status(e.getHttpStatus().getReasonPhrase()) //obtenemos el codigo y frase del http
                .message(e.getMessage()).build();
        return new ResponseEntity<>(errorDTO, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .status_code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Errores de validación: " + String.join(", ", errorMessages))
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}