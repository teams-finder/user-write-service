package com.teamsfinder.userwriteservice.user.repository;

import com.teamsfinder.userwriteservice.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByKeyCloakId(UUID keyCloakId);
    User findByKeyCloakId(UUID fromString);
}
