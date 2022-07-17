package com.teamsfinder.userwriteservice.user.service;

import com.teamsfinder.userwriteservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImp {
    private final UserRepository userRepository;
}
