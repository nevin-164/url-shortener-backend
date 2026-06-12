package com.example.urlshortener.util;

public class Base62Encoder {

    // Our 62-character alphabet
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public static String encode(Long databaseId) {
        StringBuilder shortKey = new StringBuilder();

        // The core math loop!
        while (databaseId > 0) {
            int remainder = (int) (databaseId % BASE);
            shortKey.append(ALPHABET.charAt(remainder));
            databaseId = databaseId / BASE;
        }

        return shortKey.reverse().toString();
    }
}