package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityIsAlreadyExistException;

public class FavouriteLocationIsAlreadyExistException extends Exception implements EntityIsAlreadyExistException {
	@Override
	public String getMessage() {
		return "Location is already added as favourite";
	}
}
