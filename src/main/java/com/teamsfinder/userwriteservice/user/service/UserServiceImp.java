package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.dto.UserMapper;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.keycloak.KeyCloakService;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final KeyCloakService keyCloakService;

    @Override
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

    @Override
    public UserResponseDto editUser(EditUserDto editUserDto) {
        Long id = editUserDto.id();
        if(notExistsById(id)){
            throw new UserNotFoundException(id);
        }
        User updatedUser = updateUserAndReturn(id, editUserDto);
        User savedUser = saveToRepository(updatedUser);
        return UserMapper.mapUserToResponseDto(savedUser);
    }

    private User updateUserAndReturn(Long id, EditUserDto editUserDto) {
        User user = getUserFromRepository(id);
        user.setGithubProfileUrl(editUserDto.githubProfileUrl());
        user.setProfilePictureUrl(editUserDto.profilePictureUrl());
        user.setTags(UserMapper.mapTagsFromEditDto(editUserDto.tags()));
        return user;
    }

    @Override
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
