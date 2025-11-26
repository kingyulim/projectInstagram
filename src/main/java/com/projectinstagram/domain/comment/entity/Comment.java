package com.projectinstagram.domain.comment.entity;

import com.projectinstagram.common.entity.BaseTimeEntity;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="board_id")
    private Board boardId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User userId;
}
