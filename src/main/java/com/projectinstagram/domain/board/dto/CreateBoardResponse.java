package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.BoardImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateBoardResponse {
    private final Long id;
    private final String content;
    private final List<BoardImage> contentImg;
    private final LocalDateTime createdAt;

    public CreateBoardResponse(BoardDto dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        this.contentImg = dto.getContentImg();
        this.createdAt = dto.getCreatedAt();
    }

}
