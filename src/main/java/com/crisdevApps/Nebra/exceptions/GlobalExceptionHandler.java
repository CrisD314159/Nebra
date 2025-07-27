package com.crisdevApps.Nebra.exceptions;

import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.ValidationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage<String>> generalException(Exception e){
        return ResponseEntity.internalServerError().body( new ErrorMessage<>(false, e.getMessage())
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage<String>> validationException(
            MethodArgumentNotValidException ex ) {
        StringBuilder errors = new StringBuilder();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            errors.append("\n ").append(e.getField()).append(e.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body( new ErrorMessage<>(false, errors.toString()) );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage<String>> entityNotFoundExceptionHandler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage<String>(false, ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage<String>> unauthorizedExceptionHandler(UnauthorizedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage<String>(false, ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage<String>> validationExceptionHandler(ValidationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage<String>(false, ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage<String>> authenticationHandler(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorMessage<>(false, e.getMessage())
        );
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ErrorMessage<String>> jpaExceptionHandler(JpaSystemException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorMessage<>(false, e.getMessage())
        );
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorMessage<String>> unexpectedExceptionHandler(UnexpectedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorMessage<>(false, e.getMessage())
        );
    }
}
