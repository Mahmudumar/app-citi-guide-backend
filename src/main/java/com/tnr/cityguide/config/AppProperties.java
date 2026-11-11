package com.tnr.cityguide.config;

import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppProperties(Jwt jwt, Security security, Reset reset, Cors cors, Storage storage, Mail mail, Dev dev) {
    public record Jwt(String secret, Duration accessTtl, Duration refreshTtl) {}
    public record Security(int loginMaxAttempts, int loginLockSeconds) {}
    public record Reset(int codeTtlMinutes, int maxAttempts) {}
    public record Cors(List<String> allowedOriginPatterns) {}
    public record Storage(String localDir) {}
    public record Mail(String from) {}
    public record Dev(boolean returnResetCode) {}
}
