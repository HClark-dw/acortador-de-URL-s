package com.portfolio.url_shortener.dto;

import java.time.LocalDateTime;

public class ShortenResponseDto {

    private Long id;
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private long clickCount;
    private LocalDateTime createdAt;

    public ShortenResponseDto() {
    }

    public ShortenResponseDto(Long id, String originalUrl, String shortCode, String shortUrl, long clickCount, LocalDateTime createdAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
