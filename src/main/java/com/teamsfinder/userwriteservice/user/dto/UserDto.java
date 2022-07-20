package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagDto;

import java.util.List;
import java.util.UUID;

public record UserDto(Long id, String keyCloakId, String accountType, String githubProfileUrl, String profilePictureUrl, boolean blocked, List<TagDto> tags){ }
