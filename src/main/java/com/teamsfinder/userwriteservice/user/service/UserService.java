package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.tag.dto.TagMapper;
import com.teamsfinder.userwriteservice.user.dto.EditUserRequestDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.keycloak.KeycloakService;
import com.teamsfinder.userwriteservice.user.mapper.UserMapper;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keyCloakService;

    public UserResponseDto createUser(String keyCloakId) {
        User user = buildUser(keyCloakId);
        User savedUser = saveToRepository(user);
        return UserMapper.mapUserToResponseDto(savedUser);
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
        return UserMapper.mapUserToResponseDto(savedUser);
    }

    private User updateUserAndReturn(Long id, EditUserRequestDto editUserDto) {
        User user = getUserFromRepository(id);
        user.setGithubProfileUrl(editUserDto.githubProfileUrl());
        user.setProfilePictureUrl(editUserDto.profilePictureUrl());
        user.setTags(TagMapper.mapTagsFromEditDto(editUserDto.tags()));
        return user;
    }

    public UserResponseDto blockUser(Long id) {
        User user = getUserFromRepository(id);
        user.setBlocked(true);
        keyCloakService.blockInKeyCloak(user);
        User savedUser = saveToRepository(user);
        return UserMapper.mapUserToResponseDto(savedUser);
    }

    private User getUserFromRepository(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private boolean notExistsById(Long id) {
        return !userRepository.existsById(id);
    }
}
