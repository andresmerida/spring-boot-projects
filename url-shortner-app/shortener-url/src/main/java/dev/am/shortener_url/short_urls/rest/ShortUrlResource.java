package dev.am.shortener_url.short_urls.rest;

import dev.am.shortener_url.shared.dto.short_urls.ShortUrlDTO;
import dev.am.shortener_url.short_urls.domain.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/short-urls")
class ShortUrlResource {
    private final ShortUrlService shortUrlService;

    ShortUrlResource(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping
    ResponseEntity<List<ShortUrlDTO>> getShortUrls() {
        return ResponseEntity.ok().body(shortUrlService.getShortPublicUrls());
    }
}
