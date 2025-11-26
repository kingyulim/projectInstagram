package com.projectinstagram.domain.friend.repository;

import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.entity.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findByIdUserIdFrom(Long userIdFrom);
    List<Friend> findByIdUserIdTo(Long userIdTo);
    List<Friend> findByIdUserIdFromOrIdUserIdTo(Long userIdFrom, Long userIdTo);
}
