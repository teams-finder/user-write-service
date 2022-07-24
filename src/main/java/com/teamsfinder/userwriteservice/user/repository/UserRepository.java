package com.teamsfinder.userwriteservice.user.repository;

import com.teamsfinder.userwriteservice.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
