package com.company.musicstorerecommendations.exception;

public class NonMatchingIdException extends RuntimeException {
    public NonMatchingIdException(String message) { super(message); }
    public NonMatchingIdException() { super(); }
}
