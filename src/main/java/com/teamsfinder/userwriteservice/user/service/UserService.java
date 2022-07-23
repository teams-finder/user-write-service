package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(String keyCloakId);
    UserResponseDto editUser(EditUserDto editUserDto);
    UserResponseDto blockUser(Long id);
}
