package com.portfolio.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUrlNotFound(UrlNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-404";
    }

    @ExceptionHandler(InvalidUrlException.class)
    public String handleInvalidUrl(InvalidUrlException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "index";
    }
}
