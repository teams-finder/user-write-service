package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagResponseDto;

import java.util.List;

public record UserResponseDto(Long id, String keyCloakId, String accountType, String githubProfileUrl, String profilePictureUrl, boolean blocked, List<TagResponseDto> tags) {
}
