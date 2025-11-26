package com.projectinstagram.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private Long userId;
    private Long boardId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
