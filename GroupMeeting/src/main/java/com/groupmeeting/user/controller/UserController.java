package com.groupmeeting.user.controller;

import com.groupmeeting.core.exception.custom.BadRequestException;
import com.groupmeeting.core.exception.custom.ResourceNotFoundException;
import com.groupmeeting.dto.request.user.CustomUserDetails;
import com.groupmeeting.dto.request.user.GetUserDto;
import com.groupmeeting.dto.request.user.UpdateUserDeviceDto;
import com.groupmeeting.dto.request.user.UpdateUserDto;
import com.groupmeeting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<GetUserDto> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getInfo(userDetails.getId()));
    }

    @GetMapping("/{id}/token")
    public ResponseEntity<String> getToken(
            @PathVariable Long id,
            @RequestParam(required = false) int expire
    ) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/nickname/random")
    public ResponseEntity<String> randomNickname() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/{nickname}/duplicated")
    public ResponseEntity<Boolean> duplicateNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String nickname) {
        return ResponseEntity.ok(userService.duplicateNickname(nickname));
    }

    @PatchMapping("/info")
    public ResponseEntity<GetUserDto> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String nickname,
            @RequestPart(required = false) MultipartFile profileImg
    ) throws BadRequestException {
        var updateUserInfo = userService.updateInfo(userDetails.getId(), new UpdateUserDto(nickname, profileImg));
        return ResponseEntity.ok(updateUserInfo);
    }

    @PatchMapping("/device")
    public ResponseEntity<Boolean> updateMyDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateUserDeviceDto updateUserDeviceDto
    ) {
        userService.updateDeviceInfo(userDetails.getId(), updateUserDeviceDto);
        return ResponseEntity.ok().build();
    }
}
