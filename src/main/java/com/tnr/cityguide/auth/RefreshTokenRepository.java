package com.tnr.cityguide.auth;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,UUID>{
 Optional<RefreshToken> findByTokenHash(String hash);
 @Modifying @Query("update RefreshToken t set t.revoked=true where t.user.id=:userId") void revokeAll(@Param("userId") UUID userId);
}
