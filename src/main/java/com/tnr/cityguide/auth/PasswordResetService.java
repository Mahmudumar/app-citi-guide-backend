package com.tnr.cityguide.auth;
import java.util.Map;import org.springframework.stereotype.Service;
/**
 * Password-reset facade keeps the reset endpoints separate from the login and token API.
 * The transactional implementation and token hashing are shared with AuthService.
 */
@Service
public class PasswordResetService {
 private final AuthService authService;
 public PasswordResetService(AuthService authService){this.authService=authService;}
 public Map<String,Object> forgotPassword(String email){return authService.forgot(email);}
 public void resetPassword(AuthDtos.ResetRequest request){authService.reset(request);}
}
