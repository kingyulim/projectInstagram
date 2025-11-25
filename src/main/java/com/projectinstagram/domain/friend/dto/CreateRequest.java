package com.projectinstagram.domain.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRequest {
    @NotNull(message = "팔로우할 userIdTo는 필수 값입니다.")
    private Long userIdTo;
}
