package com.teamsfinder.userwriteservice.tag.dto;

import com.teamsfinder.userwriteservice.tag.model.Tag;

public record TagResponseDto(
        Long id,
        String name
) {

    public TagResponseDto(Tag tag) {
        this(tag.getId(), tag.getName());
    }
}
