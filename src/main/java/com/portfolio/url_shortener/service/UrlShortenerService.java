package com.portfolio.url_shortener.service;

import com.portfolio.url_shortener.exception.InvalidUrlException;
import com.portfolio.url_shortener.exception.UrlNotFoundException;
import com.portfolio.url_shortener.model.ShortenedUrl;
import com.portfolio.url_shortener.repository.ShortenedUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UrlShortenerService {

    private static final String BASE62_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private static final int MAX_COLLISION_RETRIES = 5;

    private final ShortenedUrlRepository repository;
    private final SecureRandom random = new SecureRandom();

    public UrlShortenerService(ShortenedUrlRepository repository) {
        this.repository = repository;
    }

    /**
     * Valida y acorta una URL larga generando un código único de 6 caracteres.
     * Incluye detección y resolución de colisiones con reintentos controlados.
     */
    @Transactional
    public ShortenedUrl shortenUrl(String originalUrl) {
        String sanitizedUrl = validateAndSanitizeUrl(originalUrl);

        String shortCode = generateUniqueShortCode();
        ShortenedUrl entity = new ShortenedUrl(sanitizedUrl, shortCode);
        return repository.save(entity);
    }

    /**
     * Busca la URL original por su código y computa el incremento de clics.
     */
    @Transactional
    public String getOriginalUrlAndIncrementClicks(String shortCode) {
        ShortenedUrl shortenedUrl = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("No se encontró ninguna URL asociada al código: " + shortCode));

        shortenedUrl.incrementClicks();
        repository.save(shortenedUrl);
        return shortenedUrl.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public List<ShortenedUrl> getAllUrls() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public long getTotalUrls() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public long getTotalClicks() {
        return repository.getTotalClicks();
    }

    @Transactional
    public void deleteUrl(Long id) {
        repository.deleteById(id);
    }

    /**
     * Generación de código aleatorio de alta entropía con resolución de colisiones.
     * Con 62 caracteres y longitud 6, existen 62^6 (~56.8 mil millones) combinaciones posibles.
     */
    private String generateUniqueShortCode() {
        for (int attempt = 1; attempt <= MAX_COLLISION_RETRIES; attempt++) {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                int index = random.nextInt(BASE62_ALPHABET.length());
                sb.append(BASE62_ALPHABET.charAt(index));
            }
            String candidateCode = sb.toString();

            if (!repository.existsByShortCode(candidateCode)) {
                return candidateCode;
            }
        }
        throw new IllegalStateException("No se pudo generar un código único después de " + MAX_COLLISION_RETRIES + " reintentos.");
    }

    /**
     * Alternativa biyectiva Base62 sobre ID numérico (Garantiza 0 colisiones en O(1)).
     * Útil como argumento en entrevistas de System Design.
     */
    public static String encodeBase62(long id) {
        if (id <= 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(BASE62_ALPHABET.charAt(remainder));
            id /= 62;
        }
        return sb.reverse().toString();
    }

    /**
     * Valida sintácticamente la URL y verifica que incluya protocolo http o https.
     */
    private String validateAndSanitizeUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new InvalidUrlException("La URL no puede estar vacía.");
        }

        String trimmed = url.trim();

        // Autocompletar protocolo si el usuario omitió https://
        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            trimmed = "https://" + trimmed;
        }

        try {
            URI uri = URI.create(trimmed);
            if (uri.getHost() == null || !uri.getHost().contains(".")) {
                throw new InvalidUrlException("La URL ingresada no posee un dominio válido.");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidUrlException("El formato de la URL no es válido: " + e.getMessage());
        }

        return trimmed;
    }
}
