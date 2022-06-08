package com.chat.reactchat.repository;

import com.chat.reactchat.model.ChatRoom;
import com.chat.reactchat.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Set<User> findUsersByIdIn(Iterable<Long> id);

    @Query(value = "select u from User u where concat(u.firstName, ' ', u.secondName) like %:name%")
    Set<User> selectUsersByFullName(@Param("name") String name);

    Boolean existsUserByIdAndRooms_Id(Long userId, Long roomId);

    Boolean existsUserByEmail(String email);

    Boolean existsUserById(Long id);

    @Query(value = "select u.id from User u left join u.rooms r where :roomId = r.id")
    Set<Long> selectUsersIdFromRoom(@Param("roomId") Long roomId);

    default User findUserByEmailOrThrow(String email) {
        return findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User: " + email + " not found."));
    }

    default User findUserByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new UsernameNotFoundException("User: " + id + " not found."));
    }
}
