package com.teamsfinder.userwriteservice.user.dto;


import com.teamsfinder.userwriteservice.tag.dto.TagDto;
import com.teamsfinder.userwriteservice.tag.dto.TagMapper;
import com.teamsfinder.userwriteservice.tag.model.Tag;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserMapper {
    public static UserDto mapToDto(User user){
        AccountType accountType = user.getAccountType();
        return new UserDto(user.getId(), user.getKeyCloakId(), accountType.toString(), user.getGithubProfileUrl(), user.getProfilePictureUrl(), user.isBlocked(), mapTagsToDto(user.getTags()));
    }

    private static List<TagDto> mapTagsToDto(List<Tag> tags) {
        return tags.stream()
                .map(TagMapper::mapToDto)
                .toList();
    }

    public static User mapFromEditDto(EditUserDto editUserDto){
        return User.builder()
                .id(editUserDto.id())
                .githubProfileUrl(editUserDto.githubProfileUrl())
                .profilePictureUrl(editUserDto.profilePictureUrl())
                .tags(mapTagsFromEditDto(editUserDto.tags()))
                .build();
    }

    private static List<Tag> mapTagsFromEditDto(List<TagDto> tags) {
        return tags.stream()
                .map(TagMapper::mapFromDto)
                .toList();
    }
}
