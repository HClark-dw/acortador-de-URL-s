package com.portfolio.url_shortener.exception;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message) {
        super(message);
    }
}
