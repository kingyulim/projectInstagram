package com.projectinstagram.domain.like.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @EmbeddedId
    private CommentLikeId id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    @MapsId("commentId")//복합키의 commentId와 매핑
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId("userId") //복합키의 userId와 매핑
    private User user;

    public CommentLike(Comment comment, User user) {
        this.id = new CommentLikeId(comment.getId(), user.getId());
        this.comment = comment;
        this.user = user;
    }

}