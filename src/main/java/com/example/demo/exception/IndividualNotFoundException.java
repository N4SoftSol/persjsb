package com.example.demo.exception;

public class IndividualNotFoundException
        extends RuntimeException {

    public IndividualNotFoundException(
            String message) {

        super(message);
    }
}