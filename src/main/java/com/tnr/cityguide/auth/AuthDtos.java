package com.tnr.cityguide.auth;
import jakarta.validation.constraints.*;
public final class AuthDtos {
 private AuthDtos(){}
 public record RegisterRequest(@NotBlank String name,@NotBlank String email,@NotBlank String password,@NotBlank String confirmPassword){}
 public record LoginRequest(String email,String password){}
 public record RefreshRequest(String refreshToken){}
 public record ForgotRequest(String email){}
 public record ResetRequest(String email,String code,String password,String confirmPassword){}
 public record UserDto(java.util.UUID id,String name,String email,String phone,String avatarUrl,String role,Prefs prefs){}
 public record Prefs(boolean events,boolean reviews,boolean news){}
 public record AuthResponse(String accessToken,String refreshToken,String tokenType,long expiresIn,UserDto user){}
}
