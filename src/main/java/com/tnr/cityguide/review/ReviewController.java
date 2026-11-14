package com.tnr.cityguide.review;
import io.swagger.v3.oas.annotations.tags.Tag;import java.util.*;import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.*;
@RestController @Tag(name="Reviews")
public class ReviewController {
 private final ReviewService service;public ReviewController(ReviewService service){this.service=service;}
 private UUID userId(Jwt jwt){return jwt==null?null:UUID.fromString(jwt.getSubject());}
 @GetMapping("/api/places/{id}/reviews") public List<ReviewService.ReviewDto> list(@PathVariable String id,@AuthenticationPrincipal Jwt jwt){return service.forPlace(id,userId(jwt));}
 public record ReviewRequest(short rating,String text){}
 @PutMapping("/api/places/{id}/reviews") public ReviewService.ReviewDto upsert(@PathVariable String id,@RequestBody ReviewRequest r,@AuthenticationPrincipal Jwt jwt){return service.upsert(id,userId(jwt),r.rating(),r.text());}
 @DeleteMapping("/api/reviews/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){service.delete(id,userId(jwt),false);return ResponseEntity.noContent().build();}
 @PutMapping("/api/reviews/{id}/like") public ReviewService.LikeState like(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){return service.like(id,userId(jwt),true);}
 @DeleteMapping("/api/reviews/{id}/like") public ReviewService.LikeState unlike(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){return service.like(id,userId(jwt),false);}
}