package com.parovsky.traver.exception.impl;

import com.parovsky.traver.exception.EntityNotFoundException;

public class LocationNotFoundException extends Exception implements EntityNotFoundException {
    @Override
    public String getMessage() {
        return "Location was not found";
    }
}
