package com.projectinstagram.domain.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateCommentResponse(Long id, Long userId, Long boardId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt ) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
