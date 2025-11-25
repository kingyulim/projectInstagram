package com.projectinstagram.domain.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateBoardResponse {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    public CreateBoardResponse(BoardDto dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        this.createdAt = dto.getCreatedAt();
    }

}
