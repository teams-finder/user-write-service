package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagResponseDto;
import com.teamsfinder.userwriteservice.tag.model.Tag;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;

import java.util.List;

public record UserResponseDto(

        Long id,

        String username,

        String keyCloakId,

        AccountType accountType,

        String githubProfileUrl,

        String profilePictureUrl,

        boolean blocked,

        List<TagResponseDto> tags
) {

    public UserResponseDto(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getKeyCloakId(),
                user.getAccountType(),
                user.getGithubProfileUrl(),
                user.getProfilePictureUrl(),
                user.isBlocked(),
                mapTagsToDto(user.getTags())
        );
    }

    private static List<TagResponseDto> mapTagsToDto(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagResponseDto(tag))
                .toList();
    }
}
