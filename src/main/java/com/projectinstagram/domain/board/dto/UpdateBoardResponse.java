package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardImageRepository;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateBoardResponse {
    private Long id;
    private String content;
    private LocalDateTime modifiedAt;

    public UpdateBoardResponse(BoardDto dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        this.modifiedAt = dto.getModifiedAt();
    }
}
