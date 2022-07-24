package com.teamsfinder.userwriteservice.tag.dto;

import com.teamsfinder.userwriteservice.tag.model.Tag;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TagMapper {

    public static TagResponseDto mapTagToDto(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName());
    }

    public static Tag mapTagFromEditDto(TagEditDto tagResponseDto) {
        return Tag.builder()
                .id(tagResponseDto.id())
                .name(tagResponseDto.name())
                .build();
    }

    public static List<TagResponseDto> mapTagsToResponseDto(List<Tag> tags) {
        return tags.stream()
                .map(TagMapper::mapTagToDto)
                .toList();
    }

    public static List<Tag> mapTagsFromEditDto(List<TagEditDto> tags) {
        return tags.stream()
                .map(TagMapper::mapTagFromEditDto)
                .collect(Collectors.toList());
    }
}
