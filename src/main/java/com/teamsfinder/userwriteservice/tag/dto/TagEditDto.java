package com.teamsfinder.userwriteservice.tag.dto;

import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotEmpty;

public record TagEditDto(Long id, @NotEmpty String name) { }
