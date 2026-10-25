package com.portfolio.url_shortener.repository;

import com.portfolio.url_shortener.model.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {

    Optional<ShortenedUrl> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    List<ShortenedUrl> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(s.clickCount), 0) FROM ShortenedUrl s")
    long getTotalClicks();
}
