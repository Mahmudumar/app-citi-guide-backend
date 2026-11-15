package com.tnr.cityguide.auth;
import jakarta.validation.constraints.NotBlank;
public final class AuthDtos {
 private AuthDtos(){}
 public record RegisterRequest(@NotBlank(message="Please enter your full name.") String name,@NotBlank(message="Enter a valid email address.") String email,String password,String confirmPassword){}
 public record LoginRequest(String email,String password){}public record RefreshRequest(String refreshToken){}public record ForgotRequest(String email){}public record ResetRequest(String email,String code,String password,String confirmPassword){}
 public record UserDto(java.util.UUID id,String name,String email,String phone,String avatarUrl,String role,Prefs prefs){}public record Prefs(boolean events,boolean reviews,boolean news){}
 public record AuthResponse(String accessToken,String refreshToken,String tokenType,long expiresIn,UserDto user){}
}
