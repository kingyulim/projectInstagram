package com.projectinstagram.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectinstagram.common.entity.BaseTimeEntity;
import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
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

    @JsonBackReference
    @OneToMany(mappedBy = "boardId")
    private List<BoardImage> images;
    private String content;

    public Board(User user, CreateBoardRequest request) {
        this.userId = user;
        this.content = request.getContent();
    }

    public static Board from(User user, CreateBoardRequest request) {
        return new Board(user, request);
    }
}
