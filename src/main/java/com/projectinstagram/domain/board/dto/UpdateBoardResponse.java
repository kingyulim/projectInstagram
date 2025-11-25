package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateBoardResponse {
    private Long id;
    private String content;
    private List<String> contentimg;
    private LocalDateTime modifiedAt;

    public UpdateBoardResponse(Board board) {
        this.id = board.getId();
        this.content = board.getContent();
        this.contentimg = board.getContentImg();
        this.modifiedAt = board.getModifiedAt();
    }

    public static UpdateBoardResponse form(Board board) {
        return new UpdateBoardResponse(board);
    }
}
