package com.projectinstagram.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final Long id;
    private final String name;
    private final String nickName;
}