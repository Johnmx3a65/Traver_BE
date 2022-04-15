package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityIsAlreadyExistException;

public class CategoryIsAlreadyExistException extends Exception implements EntityIsAlreadyExistException {
    @Override
    public String getMessage() {
        return "Category is already exist";
    }
}
