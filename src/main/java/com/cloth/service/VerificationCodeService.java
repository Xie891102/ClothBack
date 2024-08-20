package com.cloth.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationCodeService {
    private static final long EXPIRATION_TIME_MS = 10 * 60 * 1000; // 10 minutes

    private Map<String, VerificationCodeEntry> verificationCodes = new HashMap<>();

    public void saveVerificationCode(String email, String code) {
        VerificationCodeEntry entry = new VerificationCodeEntry(code, LocalDateTime.now());
        verificationCodes.put(email, entry);
    }

    public boolean isValidVerificationCode(String email, String code) {
        VerificationCodeEntry entry = verificationCodes.get(email);
        if (entry != null && entry.getCode().equals(code)) {
            // Check if code is expired
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expirationTime = entry.getCreatedAt().plusSeconds(EXPIRATION_TIME_MS / 1000);
            if (now.isBefore(expirationTime)) {
                verificationCodes.remove(email); // 驗證成功後刪除驗證碼
                return true;
            }
        }
        return false;
    }

    private static class VerificationCodeEntry {
        private String code;
        private LocalDateTime createdAt;

        public VerificationCodeEntry(String code, LocalDateTime createdAt) {
            this.code = code;
            this.createdAt = createdAt;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
