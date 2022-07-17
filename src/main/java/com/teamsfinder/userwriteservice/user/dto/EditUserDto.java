package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagDto;

import java.util.List;
import java.util.UUID;

public record EditUserDto(Long id, String githubProfileUrl, String profilePictureUrl, List<TagDto> tags) {
}
