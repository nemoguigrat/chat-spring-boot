package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Set<User> findUsersByIdIn(Set<Long> id);

    Boolean existsUserByEmail(String email);

    // не нашел других вариантов пока :/
    @Query(value = "select u from ChatRoom r left join r.users u where :room member of u.rooms")
    Set<User> selectUsersFromRoom(@Param("room") ChatRoom roomId);
}
