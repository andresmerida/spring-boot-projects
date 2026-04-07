package dev.am.shortener_url.short_urls.domain;

import dev.am.shortener_url.shared.dto.short_urls.ShortUrlDTO;
import org.springframework.stereotype.Component;

@Component
class ShortUrlMapper {

    ShortUrlDTO toDTO(ShortUrlEntity entity) {
        return new ShortUrlDTO(
                entity.getId(),
                entity.getShortKey(),
                entity.getOriginalUrl(),
                entity.getExpiresAt()
        );
    }
}
