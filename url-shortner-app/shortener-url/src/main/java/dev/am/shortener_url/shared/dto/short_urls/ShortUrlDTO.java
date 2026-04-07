package dev.am.shortener_url.shared.dto.short_urls;

import java.time.Instant;

public record ShortUrlDTO (Long id, String shortKey, String longUrl, Instant expirationDate) {
}
