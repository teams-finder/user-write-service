package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagResponseDto;
import com.teamsfinder.userwriteservice.user.model.AccountType;

import java.util.List;

public record UserResponseDto(
        Long id,
        String keyCloakId,
        AccountType accountType,
        String githubProfileUrl,
        String profilePictureUrl,
        boolean blocked,
        List<TagResponseDto> tags
) {

}
