package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserDto;

import java.security.Principal;

public interface UserService {
    UserDto getUserOrCreate(Principal principal);
    UserDto editUser(EditUserDto editUserDto);
}
