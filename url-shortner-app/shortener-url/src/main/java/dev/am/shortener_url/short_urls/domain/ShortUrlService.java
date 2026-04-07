package dev.am.shortener_url.short_urls.domain;

import dev.am.shortener_url.shared.dto.short_urls.ShortUrlDTO;

import java.util.List;

public interface ShortUrlService {
    List<ShortUrlDTO> getShortPublicUrls();
}
