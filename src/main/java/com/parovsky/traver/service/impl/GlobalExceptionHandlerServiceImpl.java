package com.parovsky.traver.service.impl;

import com.parovsky.traver.exception.ApplicationException;
import com.parovsky.traver.service.GlobalExceptionHandlerService;
import com.parovsky.traver.utils.Constraints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.parovsky.traver.exception.Errors.*;

@Slf4j
@Service
public class GlobalExceptionHandlerServiceImpl extends DefaultErrorAttributes implements GlobalExceptionHandlerService {

	@Override
	public ResponseEntity<String> handleException(HttpMessageNotReadableException e) {
		log.error("Parsing error");
		return new ResponseEntity<>("Error parsing request body.", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Map<String, Object>> handleException(AuthenticationException e, WebRequest request) {
		if (e.getCause() instanceof ApplicationException) {
			ApplicationException ex = (ApplicationException) e.getCause();
			return ofType(ex, ex.getErrorResponse().getHttpStatus(), request);
		}
		if (e instanceof BadCredentialsException) {
			return ofType(request, BAD_CREDENTIALS.getHttpStatus(), BAD_CREDENTIALS.getMessage(), BAD_CREDENTIALS.getKey(), Collections.emptyList());
		}
		return ofType(request, UNAUTHORIZED.getHttpStatus(), UNAUTHORIZED.getMessage(), UNAUTHORIZED.getMessage(), Collections.emptyList());
	}

	@Override
	public ResponseEntity<Map<String, Object>> handleException(ApplicationException e, WebRequest request) {
		return ofType(e, e.getErrorResponse().getHttpStatus(), request);
	}

	@Override
	public ResponseEntity<Map<String, Object>> handleException(Throwable e, WebRequest request) {
		log.error(e.getMessage());
		return ofType(request, SERVER_ERROR.getHttpStatus(), SERVER_ERROR.getMessage(), SERVER_ERROR.getKey(), Collections.emptyList());
	}

	protected ResponseEntity<Map<String, Object>> ofType(ApplicationException e, HttpStatus status,
														 WebRequest request) {
		return ofType(request, status, e.getMessage(), e.getErrorResponse().getKey(), Collections.emptyList());
	}

	private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
													   String key, List validationErrors) {
		Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		attributes.put(Constraints.STATUS, status.value());
		attributes.put(Constraints.ERROR, status);
		attributes.put(Constraints.MESSAGE, message);
		attributes.put(Constraints.ERRORS, validationErrors);
		attributes.put(Constraints.ERROR_KEY, key);
		attributes.put(Constraints.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
		return new ResponseEntity<>(attributes, status);
	}

}
