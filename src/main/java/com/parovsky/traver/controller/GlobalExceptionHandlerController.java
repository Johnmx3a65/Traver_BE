package com.parovsky.traver.controller;

import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.UnprocessableEntityException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import com.parovsky.traver.service.GlobalExceptionHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class GlobalExceptionHandlerController {

    private final GlobalExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleExceptions(EntityNotFoundException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handleExceptions(EntityAlreadyExistsException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleExceptions(AuthenticationException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(VerificationCodeNotMatchException.class)
    public ResponseEntity<String> handleExceptions(VerificationCodeNotMatchException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<String> handleExceptions(UnprocessableEntityException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleExceptions(HttpMessageNotReadableException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Throwable e) {
        return exceptionHandlerService.handleException(e);
    }
}
