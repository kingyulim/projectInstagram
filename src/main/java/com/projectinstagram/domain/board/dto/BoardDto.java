package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.entity.BoardImage;
import com.projectinstagram.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardDto {
    private final Long id;
    private final User userId;
    private final String content;
    private final List<BoardImage> contentImg;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BoardDto(Board board){
        this.id = board.getId();
        this.userId = board.getUserId();
        this.content = board.getContent();
        this.contentImg = board.getImages();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public static BoardDto from(Board board) {
        return new BoardDto(board);
    }
}
