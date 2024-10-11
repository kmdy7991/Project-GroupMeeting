package com.groupmeeting.notification.repository;

import com.groupmeeting.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByScheduledAtBeforeAndReadAtNotNullAndSentAtNull(LocalDateTime scheduledAt);

    List<Notification> findByUserIdAndSentAtIsNull(Long userId);
}
