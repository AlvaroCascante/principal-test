package com.example.principaltest.exceptions;

public class RecordConflictException extends RuntimeException {
    public RecordConflictException(String message) {
        super(message);
    }
}
