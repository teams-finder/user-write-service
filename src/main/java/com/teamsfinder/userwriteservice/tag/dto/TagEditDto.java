package com.teamsfinder.userwriteservice.tag.dto;

import javax.validation.constraints.NotEmpty;

public record TagEditDto(
        Long id,
        @NotEmpty String name) {

}
