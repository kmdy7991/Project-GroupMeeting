package com.groupmeeting.dto.request.user;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDto(String nickname, MultipartFile profileImg) {
}
