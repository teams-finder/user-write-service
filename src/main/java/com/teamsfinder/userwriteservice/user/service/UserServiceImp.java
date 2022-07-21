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
        User savedUser = save(user);
        return mapUserToDto(savedUser);
    }

    private User save(User user) {
        return userRepository.save(user);
    }

    private User buildUser(String keyCloakId) {
        return User.builder()
                .keyCloakId(keyCloakId)
                .build();
    }

    private UserDto mapUserToDto(User user) {
        return UserMapper.mapToDto(user);
    }

    @Override
    public UserDto editUser(EditUserDto editUserDto) {
        Long id = editUserDto.id();
        if(!existsById(id)){
            throw new UserNotFoundException(id);
        }
        User user = getUserById(id);
        updateByEditDto(user, editUserDto);
        User savedUser = save(user);
        return mapUserToDto(savedUser);
    }

    private void updateByEditDto(User user, EditUserDto editUserDto) {
        user.setGithubProfileUrl(editUserDto.githubProfileUrl());
        user.setProfilePictureUrl(editUserDto.profilePictureUrl());
        user.setTags(UserMapper.mapTagsFromDto(editUserDto.tags()));
    }

    @Override
    public UserDto blockUser(Long id) {
        User user = getUserById(id);
        user.setBlocked(true);
        try{
            blockInKeyCloak(user.getKeyCloakId());
        } catch (Exception exception){
            throw new KeyCloakException();
        }
        User savedUser = save(user);
        return mapUserToDto(savedUser);
    }

    private void blockInKeyCloak(String keyCloakId) {
        Keycloak keycloak = buildKeyCloak();
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(keyCloakId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(false);
        userResource.update(userRepresentation);
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

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
