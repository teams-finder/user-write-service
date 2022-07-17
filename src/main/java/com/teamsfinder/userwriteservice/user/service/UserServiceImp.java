package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserDto;
import com.teamsfinder.userwriteservice.user.dto.UserMapper;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserDto getUserOrCreate(Principal principal) {
        String keyCloakId = principal.getName();
        if(existsByKeyCloakId(keyCloakId)){
            User user = getUser(keyCloakId);
            return mapUserToDto(user);
        }
        User user = buildUser(principal);
        User savedUser = save(user);
        return mapUserToDto(savedUser);
    }

    private User save(User user) {
        return userRepository.save(user);
    }

    private User buildUser(Principal principal) {
        return User.builder()
                .keyCloakId(UUID.fromString(principal.getName()))
                .build();
    }

    private UserDto mapUserToDto(User user) {
        return UserMapper.mapToDto(user);
    }

    private User getUser(String keyCloakId) {
        return userRepository.findByKeyCloakId(UUID.fromString(keyCloakId));
    }

    private boolean existsByKeyCloakId(String uuid) {
        return userRepository.existsByKeyCloakId(UUID.fromString(uuid));
    }

    @Override
    public UserDto editUser(EditUserDto editUserDto) {
        Long id = editUserDto.id();
        if(!existsById(id)){
            throw new UserNotFoundException(id);
        }
        User user = mapFromEditDto(editUserDto);
        User savedUser = save(user);
        return mapUserToDto(savedUser);
    }

    private boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    private User mapFromEditDto(EditUserDto editUserDto) {
        return UserMapper.mapFromEditDto(editUserDto);
    }
}
