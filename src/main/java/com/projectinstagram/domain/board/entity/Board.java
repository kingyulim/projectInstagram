package com.projectinstagram.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectinstagram.common.entity.BaseTimeEntity;
import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.dto.UpdateBoardRequest;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.like.entity.BoardLike;
import com.projectinstagram.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @JsonBackReference
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> images = new ArrayList<BoardImage>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @JsonBackReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> likes = new ArrayList<BoardLike>();

    @JsonBackReference
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<Comment>();

    public Board(User user, CreateBoardRequest request) {
        this.userId = user;
        this.content = request.getContent();
    }

    public void setImages(BoardImage image){
        this.images.add(image);
        image.setBoardId(this);
    }

    public void setContent(UpdateBoardRequest request) {
        this.content = request.getContent();
    }

    public static Board from(User user, CreateBoardRequest request) {
        return new Board(user, request);
    }
}
