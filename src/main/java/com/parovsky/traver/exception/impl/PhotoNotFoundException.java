package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityNotFoundException;

public class PhotoNotFoundException extends Exception implements EntityNotFoundException {
	@Override
	public String getMessage() {
		return "Photo was not found";
	}
}
