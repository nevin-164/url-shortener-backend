package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController // Tells Spring Boot: "This class listens to internet traffic!"
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // --- The Front Door for Shortening Links ---
    // Listens for a POST request at localhost:8080/api/v1/shorten
    @PostMapping("/api/v1/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {

        // Grab the long URL the user sent us
        String longUrl = request.get("longUrl");

        // Ask our Service/Brain to shorten it
        String shortKey = urlService.shortenUrl(longUrl);

        // Construct the final tiny link
        String shortUrl = "http://localhost:8080/" + shortKey;

        // Hand the final link back to the user
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    // --- The Front Door for Redirecting ---
    // Listens for ANY traffic at localhost:8080/something
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {

        // Ask our Service/Brain for the original Long URL
        String originalUrl = urlService.getOriginalUrl(shortKey);

        // Tell the user's web browser to instantly redirect (HTTP 302 FOUND) to that massive link
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}