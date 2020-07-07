package com.ghost.maremi01.exception;

public class PersonalNotFoundException extends RuntimeException {
    public PersonalNotFoundException(Long id) {
        super("Could not find the object" + id);
    }
}
