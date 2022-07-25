package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.exception.KeycloakException;
import com.teamsfinder.userwriteservice.user.exception.UserNotFoundException;
import com.teamsfinder.userwriteservice.user.keycloak.KeycloakService;
import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    private static final String USER_KEYCLOAK_ID = "KEYCLOAKID";
    private static final String EDIT_STRING = "EDITED";
    private static final String USER_GITHUB = "GITHUB";
    private static final String USER_PICTURE = "PICTURE";

    @Mock
    private UserRepository userRepository;
    @Mock
    private KeycloakService keyCloakService;

    @InjectMocks
    private UserService underTest;

    private User testUser = User.builder()
            .id(1L)
            .keyCloakId(USER_KEYCLOAK_ID)
            .accountType(AccountType.USER)
            .githubProfileUrl(USER_GITHUB)
            .profilePictureUrl(USER_PICTURE)
            .blocked(false)
            .tags(new ArrayList<>())
            .build();

    @Test
    void shouldCreateUser() {
        //given
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(userRepository.save(Mockito.any(User.class)));
        //when
        UserResponseDto userDto = underTest.createUser(USER_KEYCLOAK_ID);
        //then
        assertThat(userDto.id()).isEqualTo(1L);
        assertThat(userDto.keyCloakId()).isEqualTo(USER_KEYCLOAK_ID);
        assertThat(userDto.accountType()).isEqualTo(AccountType.USER.toString());
        assertThat(userDto.githubProfileUrl()).isEqualTo(USER_GITHUB);
        assertThat(userDto.profilePictureUrl()).isEqualTo(USER_PICTURE);
        assertThat(userDto.blocked()).isEqualTo(false);
        assertThat(userDto.tags().size()).isEqualTo(0);
    }

    @Test
    void shouldEditUser() {
        //given
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testUser));
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(userRepository.save(Mockito.any(User.class)));
        //when
        UserResponseDto userDto = underTest.editUser(new EditUserDto(1L, EDIT_STRING, EDIT_STRING, new ArrayList<>()));
        //then
        assertThat(userDto.id()).isEqualTo(1L);
        assertThat(userDto.keyCloakId()).isEqualTo(USER_KEYCLOAK_ID);
        assertThat(userDto.accountType()).isEqualTo(AccountType.USER.toString());
        assertThat(userDto.githubProfileUrl()).isEqualTo(EDIT_STRING);
        assertThat(userDto.profilePictureUrl()).isEqualTo(EDIT_STRING);
        assertThat(userDto.blocked()).isEqualTo(false);
        assertThat(userDto.tags().size()).isEqualTo(0);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhileEditingUser() {
        //given
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(false);
        //when
        Executable executableEditUser = () -> underTest.editUser(new EditUserDto(1L, EDIT_STRING, EDIT_STRING, new ArrayList<>()));
        //then
        assertThrows(UserNotFoundException.class, executableEditUser);
    }

    @Test
    void shouldBlockUser() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.doNothing().when(keyCloakService).blockInKeyCloak(Mockito.any(User.class));
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(userRepository.save(Mockito.any(User.class)));
        //when
        UserResponseDto userDto = underTest.blockUser(1L);
        //then
        assertThat(userDto.id()).isEqualTo(1L);
        assertThat(userDto.keyCloakId()).isEqualTo(USER_KEYCLOAK_ID);
        assertThat(userDto.accountType()).isEqualTo(AccountType.USER.toString());
        assertThat(userDto.githubProfileUrl()).isEqualTo(USER_GITHUB);
        assertThat(userDto.profilePictureUrl()).isEqualTo(USER_PICTURE);
        assertThat(userDto.blocked()).isEqualTo(true);
        assertThat(userDto.tags().size()).isEqualTo(0);
    }

    @Test
    void shouldThrowKeyCloakExceptionWhileBlockingUser() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.doThrow(new KeycloakException()).when(keyCloakService).blockInKeyCloak(Mockito.any(User.class));
        //when
        Executable executableBlockUser = () -> underTest.blockUser(1L);
        //then
        assertThrows(KeycloakException.class, executableBlockUser);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhileBlockingUser() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        //when
        Executable executableBlockUser = () -> underTest.blockUser(1L);
        //then
        assertThrows(UserNotFoundException.class, executableBlockUser);
    }
}