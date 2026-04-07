package dev.am.shortener_url.short_urls.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {

    @Query("SELECT su FROM ShortUrlEntity su WHERE su.isPrivate = false order by su.createdAt desc ")
    List<ShortUrlEntity> findPublicShortUrls();
}
