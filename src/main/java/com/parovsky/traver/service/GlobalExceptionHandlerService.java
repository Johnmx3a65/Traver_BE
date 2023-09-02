package com.parovsky.traver.service;

import com.parovsky.traver.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public interface GlobalExceptionHandlerService {

	ResponseEntity<String> handleException(HttpMessageNotReadableException e);

	ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e);

	ResponseEntity<Map<String, Object>> handleException(AuthenticationException e, WebRequest request);

	ResponseEntity<Map<String, Object>> handleException(ApplicationException ex, WebRequest request);

	ResponseEntity<Map<String, Object>> handleException(Throwable e, WebRequest request);
}
