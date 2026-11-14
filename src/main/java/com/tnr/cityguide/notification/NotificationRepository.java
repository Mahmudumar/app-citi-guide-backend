package com.tnr.cityguide.notification;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepository extends JpaRepository<Notification,UUID>{List<Notification> findTop100ByUserIdOrderByCreatedAtDesc(UUID userId);Optional<Notification> findByIdAndUserId(UUID id,UUID userId);}