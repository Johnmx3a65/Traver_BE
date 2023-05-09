package com.parovsky.traver.service;

import org.springframework.http.ResponseEntity;

public interface GlobalExceptionHandlerService {
	ResponseEntity<String> handleExceptions(Throwable e);
}
