package com.example.urlshortener.model;

import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2083)
    private String longUrl;

    @Column(unique = true)
    private String shortKey;

    public UrlMapping() {}

    public UrlMapping(String longUrl) {
        this.longUrl = longUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLongUrl() { return longUrl; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }

    public String getShortKey() { return shortKey; }
    public void setShortKey(String shortKey) { this.shortKey = shortKey; }
}