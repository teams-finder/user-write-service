package com.teamsfinder.userwriteservice.tag.model;

import com.teamsfinder.userwriteservice.tag.dto.TagEditDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Tag(TagEditDto tagEditDto) {
        this.id = tagEditDto.id();
        this.name = tagEditDto.name();
    }
}
