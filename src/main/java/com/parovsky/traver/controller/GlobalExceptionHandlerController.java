package com.parovsky.traver.controller;

import com.parovsky.traver.service.GlobalExceptionHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class GlobalExceptionHandlerController {

    private final GlobalExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Throwable e) {
        return exceptionHandlerService.handleExceptions(e);
    }
}
