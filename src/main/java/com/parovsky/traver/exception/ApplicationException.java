package com.parovsky.traver.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class ApplicationException extends RuntimeException {
	@Getter
	private final ErrorResponse errorResponse;

	private final Map<String, Object> messageArguments;

	public ApplicationException(ErrorResponse errorResponse, Map<String, Object> messageArguments) {
		this.errorResponse = errorResponse;
		this.messageArguments = messageArguments;
	}

	public ApplicationException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
		this.messageArguments = Collections.emptyMap();
	}

	@Override
	public String getMessage() {
		if (messageArguments.isEmpty()) {
			return errorResponse.getMessage();
		}
		StringSubstitutor sub = new StringSubstitutor(messageArguments);
		return sub.replace(errorResponse.getMessage());
	}
}
