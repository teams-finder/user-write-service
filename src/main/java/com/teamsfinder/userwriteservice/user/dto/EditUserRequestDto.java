package com.teamsfinder.userwriteservice.user.dto;

import com.teamsfinder.userwriteservice.tag.dto.TagEditDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record EditUserRequestDto(

        @NotNull
        Long id,

        @NotEmpty
        String githubProfileUrl,

        @NotEmpty
        String profilePictureUrl,

        @NotNull
        List<TagEditDto> tags
) {

}
