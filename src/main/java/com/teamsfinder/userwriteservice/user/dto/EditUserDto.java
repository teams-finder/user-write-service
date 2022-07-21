package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public record EditUserDto(@NotNull Long id, @NotEmpty String githubProfileUrl, @NotEmpty String profilePictureUrl, @NotNull List<TagDto> tags) { }
