package com.tnr.cityguide.admin;
import com.tnr.cityguide.notification.NotificationService;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/notifications")
public class AdminNotificationController {
 private final NotificationService notifications;
 public AdminNotificationController(NotificationService notifications){this.notifications=notifications;}
 @PostMapping
 public BroadcastResult send(@RequestBody BroadcastRequest request){
  String category=request.category()==null||request.category().isBlank()?"NEWS":request.category().toUpperCase(Locale.ROOT);
  int recipients=notifications.broadcast(category,request.title(),request.message(),request.targetUrl());
  return new BroadcastResult(category,recipients);
 }
 public record BroadcastRequest(String category,String title,String message,String targetUrl){}
 public record BroadcastResult(String category,int recipients){}
}