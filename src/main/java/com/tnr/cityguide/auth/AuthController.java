package com.tnr.cityguide.auth;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @Tag(name="Authentication")
public class AuthController {
 private final AuthService service;
 public AuthController(AuthService service){this.service=service;}
 @PostMapping("/register") public ResponseEntity<AuthDtos.AuthResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest r){return ResponseEntity.status(201).body(service.register(r));}
 @PostMapping("/login") public AuthDtos.AuthResponse login(@RequestBody AuthDtos.LoginRequest r){return service.login(r);}
 @PostMapping("/refresh") public AuthDtos.AuthResponse refresh(@RequestBody AuthDtos.RefreshRequest r){return service.refresh(r.refreshToken());}
 @PostMapping("/logout") public ResponseEntity<Void> logout(@RequestBody AuthDtos.RefreshRequest r){service.logout(r.refreshToken());return ResponseEntity.noContent().build();}
 @PostMapping("/forgot-password") public ResponseEntity<Map<String,Object>> forgot(@RequestBody AuthDtos.ForgotRequest r){return ResponseEntity.accepted().body(service.forgot(r.email()));}
 @PostMapping("/reset-password") public ResponseEntity<Void> reset(@RequestBody AuthDtos.ResetRequest r){service.reset(r);return ResponseEntity.noContent().build();}
}
