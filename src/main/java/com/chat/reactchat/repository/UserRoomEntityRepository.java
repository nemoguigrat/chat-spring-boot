package com.chat.reactchat.repository;

import com.chat.reactchat.model.UserRoomEntity;
import com.chat.reactchat.model.UserRoomsKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRoomEntityRepository extends CrudRepository<UserRoomEntity, UserRoomsKey> {
    Boolean existsById_UserIdAndId_RoomId(Long userId, Long roomId);

    @Query(value = "select u from UserRoomEntity u join fetch u.user " +
            "left join fetch u.room where :userId = u.id.userId and :roomId = u.id.roomId")
    Optional<UserRoomEntity> findUserRoomEntityUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);

    Set<UserRoomEntity> findUserRoomEntitiesById_UserId(Long id);
}
