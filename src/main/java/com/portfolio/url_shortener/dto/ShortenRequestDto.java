package com.portfolio.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

public class ShortenRequestDto {

    @NotBlank(message = "La URL es requerida")
    private String url;

    public ShortenRequestDto() {
    }

    public ShortenRequestDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
