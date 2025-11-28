package com.projectinstagram.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectinstagram.common.entity.BaseTimeEntity;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.like.entity.CommentLike;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name="comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="board_id")
    private Board boardId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User userId;

    @JsonBackReference
    @OneToMany(mappedBy ="comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLike;

    @Column(length = 150, nullable = false)
    private String content;

    public Comment(Board boardId, User userId, String content) {
        this.boardId = boardId;
        this.userId = userId;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

}
