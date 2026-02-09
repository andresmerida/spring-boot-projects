package dev.amerida.sb4_security.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return new StringBuilder(String.valueOf(rawPassword)).reverse().toString();
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        Assert.notNull(encodedPassword, "Password cannot be null");
        assert hashedPassword != null;
        return hashedPassword.equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(@Nullable String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }
}
