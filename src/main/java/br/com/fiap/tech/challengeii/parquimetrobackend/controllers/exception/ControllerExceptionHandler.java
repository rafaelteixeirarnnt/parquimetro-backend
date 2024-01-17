package br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {
    private StandardError err = new StandardError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ControllerNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setTimestamp(LocalDateTime.now());
        err.setStatus(status.value());
        err.setError("Entit not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidateError validateError = new ValidateError();

        validateError.setTimestamp(LocalDateTime.now());
        validateError.setStatus(e.getBody().getStatus());
        validateError.setError(e.getBody().getDetail());
        validateError.setMessage(e.getMessage());
        validateError.setPath(request.getRequestURI());

        for (FieldError f: e.getBindingResult().getFieldErrors()) {
            validateError.addMessage(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(e.getBody().getStatus()).body(validateError);
    }
}