package com.tnr.cityguide.auth;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,UUID>{
 List<PasswordResetToken> findByUserIdAndUsedAtIsNull(UUID userId);
}
