package com.teamsfinder.userwriteservice.user.creator;

import com.teamsfinder.userwriteservice.user.model.AccountType;
import com.teamsfinder.userwriteservice.user.model.User;
import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;

    public User create() {
        return userRepository.save(User.builder()
                .id(1L)
                .keyCloakId("USER_KEYCLOAK_ID")
                .accountType(AccountType.USER)
                .githubProfileUrl("USER_GITHUB")
                .profilePictureUrl("USER_PICTURE")
                .blocked(false)
                .tags(new ArrayList<>())
                .build());
    }
}
