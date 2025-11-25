package com.projectinstagram.domain.like.entity;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    @MapsId("commentId")//복합키의 commentId와 매핑
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId("userId") //복합키의 userId와 매핑
    private User user; //Q. user_id와 맵핑여부는 위에 나옴. user로 적어야 repository에서 쿼리문이 이해가 잘됨.


    public CommentLike(Comment comment, User user) {
        this.id = new CommentLikeId(comment.getId(), user.getId());
        this.comment = comment;
        this.user = user;
    }

}