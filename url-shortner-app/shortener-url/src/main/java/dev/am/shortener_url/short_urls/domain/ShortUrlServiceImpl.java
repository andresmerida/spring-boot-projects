package dev.am.shortener_url.short_urls.domain;

import dev.am.shortener_url.shared.dto.short_urls.ShortUrlDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlMapper shortUrlMapper;

    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, ShortUrlMapper shortUrlMapper) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlMapper = shortUrlMapper;
    }

    @Override
    public List<ShortUrlDTO> getShortPublicUrls() {
        return shortUrlRepository.findPublicShortUrls().stream()
                .map(shortUrlMapper::toDTO)
                .toList();
    }
}
