package com.projectinstagram.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProfileViewResponse {
    private final Long id;
    private final String nickname;
    private final String name;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
