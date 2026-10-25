package com.portfolio.url_shortener;

import com.portfolio.url_shortener.exception.InvalidUrlException;
import com.portfolio.url_shortener.model.ShortenedUrl;
import com.portfolio.url_shortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlShortenerServiceTest {

    @Autowired
    private UrlShortenerService service;

    @Test
    void testShortenUrl_GeneratesSixCharacterCode() {
        String longUrl = "https://spring.io/projects/spring-boot";
        ShortenedUrl result = service.shortenUrl(longUrl);

        assertNotNull(result);
        assertNotNull(result.getShortCode());
        assertEquals(6, result.getShortCode().length(), "El código generado debe ser exactamente de 6 caracteres");
        assertEquals(longUrl, result.getOriginalUrl());
        assertEquals(0, result.getClickCount());
    }

    @Test
    void testRedirect_IncrementsClickCount() {
        ShortenedUrl shortened = service.shortenUrl("https://github.com");
        String code = shortened.getShortCode();

        String original = service.getOriginalUrlAndIncrementClicks(code);
        assertEquals("https://github.com", original);

        // Volver a consultar la URL y verificar el conteo incrementado
        service.getOriginalUrlAndIncrementClicks(code);
        ShortenedUrl updated = service.getAllUrls().stream()
                .filter(u -> u.getShortCode().equals(code))
                .findFirst()
                .orElse(null);

        assertNotNull(updated);
        assertEquals(2, updated.getClickCount(), "El contador de clics debe reflejar cada redirección");
    }

    @Test
    void testInvalidUrl_ThrowsException() {
        assertThrows(InvalidUrlException.class, () -> service.shortenUrl(""));
        assertThrows(InvalidUrlException.class, () -> service.shortenUrl("no-es-un-dominio-valido"));
    }

    @Test
    void testBase62Encoding_Deterministic() {
        assertEquals("0", UrlShortenerService.encodeBase62(0));
        assertEquals("1", UrlShortenerService.encodeBase62(1));
        assertEquals("a", UrlShortenerService.encodeBase62(10));
        assertEquals("Z", UrlShortenerService.encodeBase62(61));
        assertEquals("10", UrlShortenerService.encodeBase62(62));
    }
}
