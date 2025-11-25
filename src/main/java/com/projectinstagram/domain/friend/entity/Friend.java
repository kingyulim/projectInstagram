package com.projectinstagram.domain.friend.entity;

import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="friends")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @EmbeddedId
    private FriendId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id_from", nullable = false)
    @MapsId("userIdFrom")//복합키의 userIdFrom과 매핑
    private User userIdFrom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id_to", nullable = false)
    @MapsId("userIdTo")//복합키의 userIdTo와 매핑
    private User userIdTo;

    public Friend (User userIdFrom, User userIdTo) {
        this.id = new FriendId(userIdFrom.getId(), userIdTo.getId());
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
    }

}
