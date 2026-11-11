package com.tnr.cityguide.favorite;
import io.swagger.v3.oas.annotations.tags.Tag;import java.util.*;import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/me/favorites") @Tag(name="Favourites")
public class FavoriteController {
 private final FavoriteService service;public FavoriteController(FavoriteService service){this.service=service;}
 @GetMapping public List<?> list(@AuthenticationPrincipal Jwt jwt){return service.list(UUID.fromString(jwt.getSubject()));}
 @PutMapping("/{placeId}") public ResponseEntity<Void> add(@PathVariable String placeId,@AuthenticationPrincipal Jwt jwt){service.add(UUID.fromString(jwt.getSubject()),placeId);return ResponseEntity.noContent().build();}
 @DeleteMapping("/{placeId}") public ResponseEntity<Void> remove(@PathVariable String placeId,@AuthenticationPrincipal Jwt jwt){service.remove(UUID.fromString(jwt.getSubject()),placeId);return ResponseEntity.noContent().build();}
}
