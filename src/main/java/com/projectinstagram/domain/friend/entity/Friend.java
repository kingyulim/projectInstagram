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
    //id는 복합키로 해결
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id_from")
    private User userIdFrom;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id_to")
    private User userIdTo;
}
