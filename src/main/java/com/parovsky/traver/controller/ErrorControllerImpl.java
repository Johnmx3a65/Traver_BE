package com.parovsky.traver.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        if (response.getStatus() == 404) {
            return new ResponseEntity<>("Endpoint was not found", HttpStatus.NOT_FOUND);
        } else if (response.getStatus() == 403) {
            return new ResponseEntity<>("You may not have permissions", HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
