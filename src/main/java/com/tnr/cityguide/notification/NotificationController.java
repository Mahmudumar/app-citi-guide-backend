package com.tnr.cityguide.notification;
import io.swagger.v3.oas.annotations.tags.Tag;import java.util.*;import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.security.oauth2.jwt.Jwt;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/me/notifications") @Tag(name="Notifications") public class NotificationController {
 private final NotificationService service;public NotificationController(NotificationService s){service=s;}
 @GetMapping public List<NotificationService.NotificationDto> list(@AuthenticationPrincipal Jwt jwt){return service.list(UUID.fromString(jwt.getSubject()));}
 @PutMapping("/{id}/read") public NotificationService.NotificationDto read(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){return service.markRead(UUID.fromString(jwt.getSubject()),id);}
}