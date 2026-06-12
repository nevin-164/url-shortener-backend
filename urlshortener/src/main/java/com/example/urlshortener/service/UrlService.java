package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service // This tells Spring Boot: "This is a manager class, keep it running in the background"
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // --- Action 1: The Shortening Process ---
    public String shortenUrl(String longUrl) {
        // 1. Create a new row in the database with the long URL
        UrlMapping mapping = new UrlMapping(longUrl);
        mapping = urlRepository.save(mapping); // Saving generates the ID (e.g., ID: 1)

        // 2. Pass that ID into our math formula
        String shortKey = Base62Encoder.encode(mapping.getId());

        // 3. Update the database row with the new short key
        mapping.setShortKey(shortKey);
        urlRepository.save(mapping);

        return shortKey; // Hand the short key back
    }

    // --- Action 2: The Redirection Process ---
    @org.springframework.cache.annotation.Cacheable(value = "urls", key = "#shortKey")
    public String getOriginalUrl(String shortKey) {

        // We added this print statement so we can visibly see when a Cache Miss happens!
        System.out.println("CACHE MISS: Searching MySQL database for key: " + shortKey);

        return urlRepository.findByShortKey(shortKey)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() -> new RuntimeException("Error: URL not found in database!"));
    }
}