package com.tnr.cityguide.admin;
import com.tnr.cityguide.notification.NotificationService;import io.swagger.v3.oas.annotations.tags.Tag;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin/notifications") @Tag(name="Admin notifications") public class AdminNotificationController {
 private final NotificationService service;public AdminNotificationController(NotificationService s){service=s;}
 public record BroadcastRequest(String category,String title,String message,String targetUrl){}
 @PostMapping public ResponseEntity<BroadcastResponse> broadcast(@RequestBody BroadcastRequest r){return ResponseEntity.status(201).body(new BroadcastResponse(service.broadcast(r.category(),r.title(),r.message(),r.targetUrl())));}
 public record BroadcastResponse(int recipients){}
}