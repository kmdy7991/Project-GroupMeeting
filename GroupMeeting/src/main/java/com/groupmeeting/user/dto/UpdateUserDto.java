package com.groupmeeting.user.dto;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDto(String nickname, MultipartFile profileImg) {
}
