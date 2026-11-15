package com.tnr.cityguide.user;
import com.tnr.cityguide.auth.AuthDtos;import com.tnr.cityguide.review.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;import java.util.*;import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.*;import org.springframework.web.multipart.MultipartFile;
@RestController @RequestMapping("/api/me") @Tag(name="Profile")
public class MeController {
 private final UserService service;private final ReviewService reviews;
 public MeController(UserService service,ReviewService reviews){this.service=service;this.reviews=reviews;}
 private UUID id(Jwt jwt){return UUID.fromString(jwt.getSubject());}
 @GetMapping public AuthDtos.UserDto get(@AuthenticationPrincipal Jwt jwt){return service.get(id(jwt));}
 @PatchMapping public AuthDtos.UserDto update(@AuthenticationPrincipal Jwt jwt,@RequestBody UserService.PatchRequest request){return service.update(id(jwt),request);}
 @PutMapping("/avatar") public AuthDtos.UserDto avatar(@AuthenticationPrincipal Jwt jwt,@RequestPart("file")MultipartFile file){return service.avatar(id(jwt),file);}
 @DeleteMapping("/avatar") public AuthDtos.UserDto removeAvatar(@AuthenticationPrincipal Jwt jwt){return service.removeAvatar(id(jwt));}
 public record ChangePasswordRequest(String currentPassword,String newPassword,String confirmPassword){}
 @PostMapping("/change-password") public AuthDtos.AuthResponse change(@AuthenticationPrincipal Jwt jwt,@RequestBody ChangePasswordRequest r){return service.changePassword(id(jwt),r.currentPassword(),r.newPassword(),r.confirmPassword());}
 @GetMapping("/reviews") public List<ReviewService.ReviewDto> reviews(@AuthenticationPrincipal Jwt jwt){return reviews.mine(id(jwt));}
}
