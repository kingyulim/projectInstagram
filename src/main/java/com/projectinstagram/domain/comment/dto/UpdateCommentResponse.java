package com.projectinstagram.domain.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {

    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public UpdateCommentResponse(Long id, Long userId, Long boardId, String content, Long likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt ) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
