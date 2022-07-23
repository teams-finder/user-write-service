package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserDto;
import com.teamsfinder.userwriteservice.user.dto.UserMapper;
import com.teamsfinder.userwriteservice.user.exception.KeyCloakException;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImp implements UserService{
    private static final String KEYCLOAK_AUTH_URL = "http://localhost:8080/auth";
    private static final String KEYCLOAK_REALM = "TeamsFinder";
    private static final String KEYCLOAK_MASTER_REALM = "master";
    private static final String KEYCLOAK_CLIENT_ID = "admin-cli";
    private static final String KEYCLOAK_USERNAME = "admin";
    private static final String KEYCLOAK_PASSWORD = "admin";

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(String keyCloakId) {
        User user = buildUser(keyCloakId);
        User savedUser = saveToRepository(user);
        return UserMapper.mapUserToDto(savedUser);
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
    public UserDto editUser(EditUserDto editUserDto) {
        Long id = editUserDto.id();
        if(notExistsById(id)){
            throw new UserNotFoundException(id);
        }
        User user = getUserFromRepository(id);
        User updatedUser = updateUserAndReturn(user, editUserDto);
        User savedUser = saveToRepository(updatedUser);
        return UserMapper.mapUserToDto(savedUser);
    }

    private User updateUserAndReturn(User user, EditUserDto editUserDto) {
        user.setGithubProfileUrl(editUserDto.githubProfileUrl());
        user.setProfilePictureUrl(editUserDto.profilePictureUrl());
        user.setTags(UserMapper.mapTagsFromDto(editUserDto.tags()));
        return user;
    }

    @Override
    public UserDto blockUser(Long id) {
        User user = getUserFromRepository(id);
        user.setBlocked(true);
        tryBlockInKeyCloak(user);
        User savedUser = saveToRepository(user);
        return UserMapper.mapUserToDto(savedUser);
    }

    private void tryBlockInKeyCloak(User user) {
        try{
            blockInKeyCloak(user.getKeyCloakId());
        } catch (Exception exception){
            throw new KeyCloakException();
        }
    }

    private void blockInKeyCloak(String keyCloakId) {
        Keycloak keycloak = buildKeyCloak();
        UserResource userResource = getUserResource(keyCloakId, keycloak);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(false);
        userResource.update(userRepresentation);
    }

    private UserResource getUserResource(String keyCloakId, Keycloak keycloak) {
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keyCloakId);
        return userResource;
    }

    private Keycloak buildKeyCloak() {
        return KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_AUTH_URL)
                .realm(KEYCLOAK_MASTER_REALM)
                .clientId(KEYCLOAK_CLIENT_ID)
                .username(KEYCLOAK_USERNAME)
                .password(KEYCLOAK_PASSWORD)
                .build();
    }

    private User getUserFromRepository(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private boolean notExistsById(Long id) {
        return !userRepository.existsById(id);
    }
}
