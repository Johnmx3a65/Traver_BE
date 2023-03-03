package com.parovsky.traver.controller;

import com.parovsky.traver.exception.EntityIsAlreadyExistException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Throwable e) {
        if (e instanceof EntityNotFoundException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }else if (e instanceof VerificationCodeNotMatchException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } else if (e instanceof EntityIsAlreadyExistException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        log.error(e.getMessage());
        return new ResponseEntity<>("Something went wrong. Unexpected issue...", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
