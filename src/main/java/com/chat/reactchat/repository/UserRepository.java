package com.chat.reactchat.repository;

import com.chat.reactchat.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Set<User> findUsersByIdIn(Set<Long> id);

    Boolean existsUserByEmail(String email);
}
