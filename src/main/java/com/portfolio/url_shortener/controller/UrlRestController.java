package com.portfolio.url_shortener.controller;

import com.portfolio.url_shortener.dto.ShortenRequestDto;
import com.portfolio.url_shortener.dto.ShortenResponseDto;
import com.portfolio.url_shortener.model.ShortenedUrl;
import com.portfolio.url_shortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UrlRestController {

    private final UrlShortenerService service;

    public UrlRestController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponseDto> createShortUrl(@Valid @RequestBody ShortenRequestDto requestDto,
                                                             HttpServletRequest request) {
        ShortenedUrl entity = service.shortenUrl(requestDto.getUrl());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/s/";
        
        ShortenResponseDto response = new ShortenResponseDto(
                entity.getId(),
                entity.getOriginalUrl(),
                entity.getShortCode(),
                baseUrl + entity.getShortCode(),
                entity.getClickCount(),
                entity.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/urls")
    public ResponseEntity<List<ShortenResponseDto>> getAllUrls(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/s/";
        List<ShortenResponseDto> dtoList = service.getAllUrls().stream()
                .map(entity -> new ShortenResponseDto(
                        entity.getId(),
                        entity.getOriginalUrl(),
                        entity.getShortCode(),
                        baseUrl + entity.getShortCode(),
                        entity.getClickCount(),
                        entity.getCreatedAt()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}
