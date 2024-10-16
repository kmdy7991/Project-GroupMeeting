package com.groupmeeting.user.service;

import com.groupmeeting.core.exception.custom.BadRequestException;
import com.groupmeeting.core.exception.custom.ResourceNotFoundException;
import com.groupmeeting.dto.request.user.GetUserDto;
import com.groupmeeting.dto.request.user.RandomNicknameDto;
import com.groupmeeting.dto.request.user.UpdateUserDeviceDto;
import com.groupmeeting.dto.request.user.UpdateUserDto;
import com.groupmeeting.entity.user.User;
import com.groupmeeting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public GetUserDto getInfo(Long id) throws ResourceNotFoundException {
        var user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return null;
    }

    public GetUserDto updateInfo(Long id, UpdateUserDto updateUserDto) throws BadRequestException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("user not found"));

        if (duplicateNickname(updateUserDto.nickname())) {
            throw new BadRequestException("이미 사용중인 닉네임입니다.");
        }

//        String
        if (updateUserDto.profileImg() != null) {

        }

        return null;
    }

    public Boolean duplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void updateDeviceInfo(Long id, UpdateUserDeviceDto updateUserDeviceDto) {

    }

    public void randomUserNickname() {
        RandomNicknameDto randomNicknameDto = new RandomNicknameDto();
        String nickName;

        do {
            nickName = randomNicknameDto.getRandomName();
        } while (!duplicateNickname(nickName));
    }
}
