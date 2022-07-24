package com.teamsfinder.userwriteservice.tag.dto;

import com.teamsfinder.userwriteservice.tag.model.Tag;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {
    public static TagResponseDto mapTagToDto(Tag tag){
        return new TagResponseDto(tag.getId(), tag.getName());
    }

    public static Tag mapTagFromEditDto(TagEditDto tagResponseDto){
        return Tag.builder()
                .id(tagResponseDto.id())
                .name(tagResponseDto.name())
                .build();
    }
}
