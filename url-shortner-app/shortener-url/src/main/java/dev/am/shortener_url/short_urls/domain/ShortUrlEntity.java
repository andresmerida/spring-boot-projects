package dev.am.shortener_url.short_urls.domain;

import dev.am.shortener_url.users.domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "short_urls")
class ShortUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "short_url_id_gen")
    @SequenceGenerator(name = "short_url_id_gen", sequenceName = "short_urls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "short_key", nullable = false, length = 10, unique = true)
    private String shortKey;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate = false;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ColumnDefault("0")
    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ShortUrlEntity() {
    }

    ShortUrlEntity(String shortKey, String originalUrl, Boolean isPrivate, Instant expiresAt, UserEntity createdBy, Long clickCount, Instant createdAt) {
        this.shortKey = shortKey;
        this.originalUrl = originalUrl;
        this.isPrivate = isPrivate;
        this.expiresAt = expiresAt;
        this.createdBy = createdBy;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrlEntity that = (ShortUrlEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(shortKey, that.shortKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortKey);
    }
}
