package com.projectinstagram.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @Size(max = 150, message = "댓글 내용은 최대 150자까지 가능합니다.")
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
