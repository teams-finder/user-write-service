package com.teamsfinder.userwriteservice.user.dto;


import com.teamsfinder.userwriteservice.tag.dto.TagEditDto;
import com.teamsfinder.userwriteservice.tag.dto.TagResponseDto;
import com.teamsfinder.userwriteservice.tag.dto.TagMapper;
import com.teamsfinder.userwriteservice.tag.model.Tag;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {
    public static UserResponseDto mapUserToResponseDto(User user){
        AccountType accountType = user.getAccountType();
        return new UserResponseDto(user.getId(), user.getKeyCloakId(), accountType.toString(), user.getGithubProfileUrl(), user.getProfilePictureUrl(), user.isBlocked(), TagMapper.mapTagsToResponseDto(user.getTags()));
    }
}
