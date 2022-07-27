package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.tag.dto.TagEditDto;
import com.teamsfinder.userwriteservice.tag.dto.TagResponseDto;
import com.teamsfinder.userwriteservice.tag.model.Tag;
import com.teamsfinder.userwriteservice.user.dto.EditUserRequestDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.keycloak.KeycloakService;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keyCloakService;

    public UserResponseDto createUser(String keyCloakId) {
        User user = buildUser(keyCloakId);
        User savedUser = saveToRepository(user);
        return mapUserToResponseDto(savedUser);
    }

    private User saveToRepository(User user) {
        return userRepository.save(user);
    }

    private User buildUser(String keyCloakId) {
        return User.builder()
                .keyCloakId(keyCloakId)
                .build();
    }

    public UserResponseDto editUser(EditUserRequestDto editUserDto) {
        Long id = editUserDto.id();
        if (notExistsById(id)) {
            throw new UserNotFoundException(id);
        }
        User updatedUser = updateUserAndReturn(id, editUserDto);
        User savedUser = saveToRepository(updatedUser);
        return mapUserToResponseDto(savedUser);
    }

    private UserResponseDto mapUserToResponseDto(User user) {
        AccountType accountType = user.getAccountType();
        return new UserResponseDto(
                user.getId(),
                user.getKeyCloakId(),
                accountType.toString(),
                user.getGithubProfileUrl(),
                user.getProfilePictureUrl(),
                user.isBlocked(),
                mapTagsToDto(user.getTags())
        );
    }

    private List<TagResponseDto> mapTagsToDto(List<Tag> tags) {
        return tags.stream()
                .map(tag -> mapTagToDto(tag))
                .toList();
    }

    private TagResponseDto mapTagToDto(Tag tag) {
        return new TagResponseDto(
                tag.getId(),
                tag.getName()
        );
    }

    private User updateUserAndReturn(Long id, EditUserRequestDto editUserDto) {
        User user = getUserFromRepository(id);
        user.setGithubProfileUrl(editUserDto.githubProfileUrl());
        user.setProfilePictureUrl(editUserDto.profilePictureUrl());
        user.setTags(mapTagsFromDto(editUserDto.tags()));
        return user;
    }

    private List<Tag> mapTagsFromDto(List<TagEditDto> tags) {
        return tags.stream()
                .map(tag -> mapTagFromDto(tag))
                .collect(Collectors.toList());
    }

    private Tag mapTagFromDto(TagEditDto tagDto) {
        return Tag.builder()
                .id(tagDto.id())
                .name(tagDto.name())
                .build();
    }

    public UserResponseDto blockUser(Long id) {
        User user = getUserFromRepository(id);
        user.setBlocked(true);
        keyCloakService.blockInKeyCloak(user);
        User savedUser = saveToRepository(user);
        return mapUserToResponseDto(savedUser);
    }

    private User getUserFromRepository(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private boolean notExistsById(Long id) {
        return !userRepository.existsById(id);
    }
}
