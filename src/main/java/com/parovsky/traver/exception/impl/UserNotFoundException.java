package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityNotFoundException;

public class UserNotFoundException extends Exception implements EntityNotFoundException {
    @Override
    public String getMessage() {
        return "User was not found";
    }
}
