package com.teamsfinder.userwriteservice.user.mapper;


import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class UserMapper {

    public static UserResponseDto mapUserToResponseDto(User user) {
        AccountType accountType = user.getAccountType();
        return new UserResponseDto(
                user.getId(),
                user.getKeyCloakId(),
                accountType.toString(),
                user.getGithubProfileUrl(),
                user.getProfilePictureUrl(),
                user.isBlocked(),
                new ArrayList<>());
    }
}
