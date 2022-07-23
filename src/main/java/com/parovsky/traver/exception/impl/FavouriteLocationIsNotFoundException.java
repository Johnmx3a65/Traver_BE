package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityNotFoundException;

public class FavouriteLocationIsNotFoundException extends Exception implements EntityNotFoundException {
	@Override
	public String getMessage() {
		return "There is no favourite location with such id";
	}
}
