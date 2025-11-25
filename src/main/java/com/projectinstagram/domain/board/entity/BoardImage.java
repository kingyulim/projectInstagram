package com.projectinstagram.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="board_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board boardId;
    @Column(name="file_name")
    private String fileName;

    public BoardImage(Board boardId, String fileName) {
        this.boardId = boardId;
        this.fileName = fileName;
    }
}
