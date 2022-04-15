package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityIsAlreadyExistException;

public class UserIsAlreadyExistException extends Exception implements EntityIsAlreadyExistException {
    @Override
    public String getMessage() {
        return "User is already exist";
    }
}
