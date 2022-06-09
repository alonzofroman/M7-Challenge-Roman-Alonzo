package com.company.musicstorecatalog.exception;

public class NonMatchingIdException extends RuntimeException {
    public NonMatchingIdException(String message) { super(message); }
    public NonMatchingIdException() { super(); }
}
