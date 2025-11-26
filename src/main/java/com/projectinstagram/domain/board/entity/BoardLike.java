package com.projectinstagram.domain.board.entity;

import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="board_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike {
    //복합키 넣을 예정
    @EmbeddedId
    private BoardLikeId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("boardId")
    @JoinColumn(name="board_id")
    private Board boardId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User userId;
}
