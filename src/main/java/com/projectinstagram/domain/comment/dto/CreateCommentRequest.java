package com.projectinstagram.domain.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private Long userId;
    private Long boardId;
    private String content;
}
