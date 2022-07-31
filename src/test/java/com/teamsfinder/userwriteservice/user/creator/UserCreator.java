package com.teamsfinder.userwriteservice.user.creator;

import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;

    public User create() {
        UUID uuid = UUID.randomUUID();
        return userRepository.save(User.builder()
                .id(null)
                .keyCloakId(uuid.toString())
                .accountType(AccountType.USER)
                .githubProfileUrl("USER_GITHUB")
                .profilePictureUrl("USER_PICTURE")
                .blocked(false)
                .tags(new ArrayList<>())
                .build());
    }

    public User createWithKeycloakId(String keycloakId) {
        return userRepository.save(User.builder()
                .id(null)
                .keyCloakId(keycloakId)
                .accountType(AccountType.USER)
                .githubProfileUrl("USER_GITHUB")
                .profilePictureUrl("USER_PICTURE")
                .blocked(false)
                .tags(new ArrayList<>())
                .build());
    }
}
