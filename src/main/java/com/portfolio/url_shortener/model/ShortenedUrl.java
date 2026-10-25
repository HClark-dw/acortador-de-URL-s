package com.portfolio.url_shortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls", indexes = {
    @Index(name = "idx_short_code", columnList = "shortCode", unique = true)
})
public class ShortenedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;

    @Column(nullable = false)
    private long clickCount = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastAccessedAt;

    public ShortenedUrl() {
    }

    public ShortenedUrl(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickCount = 0;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void incrementClicks() {
        this.clickCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }

    // Getters and Setters

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

    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }
}
