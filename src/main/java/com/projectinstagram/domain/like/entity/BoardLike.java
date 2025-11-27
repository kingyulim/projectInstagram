package com.projectinstagram.domain.like.entity;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "board_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike {

    @EmbeddedId
    private BoardLikeId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    @MapsId("boardId")//복합키의 boardId와 매핑
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId("userId") //복합키의 userId와 매핑
    private User user;

    public BoardLike(Board board, User user) {
        this.id = new BoardLikeId(board.getId(), user.getId());
        this.board = board;
        this.user = user;
    }
}
