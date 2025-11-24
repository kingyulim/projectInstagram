package com.projectinstagram.domain.board.entity;

import com.projectinstagram.common.entity.BaseTimeEntity;
import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name="boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User userId;
    private String content;
    private List<String> contentImg;

    public Board(User user, CreateBoardRequest request) {
        this.userId = user;
        this.content = request.getContent();
        this.contentImg = request.getContentImg();
    }

}
