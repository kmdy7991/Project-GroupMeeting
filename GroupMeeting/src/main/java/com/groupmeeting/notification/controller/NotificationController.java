package com.groupmeeting.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

//    public NotificationController(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> updateAsRead(
            @AuthenticationPrincipal DefaultUserDetails userDetails,
            @PathVariable Long id
    ) throws UnauthorizedException, ResourceNotFoundException {
        notificationService.updateAsRead(userDetails.getId(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/all/read")
    public ResponseEntity<UpdateAllAsReadResponse> updateAllAsRead(
            @AuthenticationPrincipal DefaultUserDetails userDetails
    ) {
        var readCount = notificationService.updateAllUnreadAsRead(userDetails.getId());
        return ResponseEntity.ok(new UpdateAllAsReadResponse(readCount));
    }
}
