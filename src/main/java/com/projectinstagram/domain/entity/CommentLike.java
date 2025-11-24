package com.projectinstagram.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="comment_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {
    //복합키로 해결할 예정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="comment_id")
    private Comment commentId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User userId;


}
