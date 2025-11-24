package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateBoardResponse {
    private Long id;
    private String content;
    private List<String> contentImg;
    private LocalDateTime createdAt;

    public CreateBoardResponse(Board board) {
        this.id = board.getId();
        this.content = board.getContent();
        this.contentImg = board.getContentImg();
        this.createdAt = board.getCreatedAt();
    }

    public static CreateBoardResponse from(Board board) {
        return new CreateBoardResponse(board);
    }
}
