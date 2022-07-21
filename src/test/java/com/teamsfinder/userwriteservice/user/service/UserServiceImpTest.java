package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserDto;
import com.teamsfinder.userwriteservice.user.exception.KeyCloakException;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    private static final String USER_KEYCLOAK_ID = "KEYCLOAKID";
    private static final String EDIT_STRING = "EDITED";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    private User testUser = User.builder()
            .id(1L)
            .keyCloakId(USER_KEYCLOAK_ID)
            .build();

    @Test
    void shouldEditUser() {
        //given
        //when
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);
        //then
        UserDto userDto = userService.editUser(new EditUserDto(1L, EDIT_STRING, EDIT_STRING, new ArrayList<>()));
        assertThat(userDto.id()).isEqualTo(1L);
        assertThat(userDto.keyCloakId()).isEqualTo(USER_KEYCLOAK_ID);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhileEditingUser(){
        //given
        //when
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(false);
        //then
        assertThrows(UserNotFoundException.class, () -> userService.editUser(new EditUserDto(1L, EDIT_STRING, EDIT_STRING, new ArrayList<>())));
    }

    @Test
    void shouldThrowKeyCloakExceptionWhileBlockingUser() {
        //given
        //when
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        //then
        assertThrows(KeyCloakException.class, () -> userService.blockUser(1L));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhileBlockingUser() {
        //given
        //when
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        //then
        assertThrows(UserNotFoundException.class, () -> userService.blockUser(1L));
    }
}