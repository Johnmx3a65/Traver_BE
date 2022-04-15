package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityNotFoundException;

public class CategoryNotFoundException extends Exception implements EntityNotFoundException {
    @Override
    public String getMessage() {
        return "Category was not found";
    }
}
