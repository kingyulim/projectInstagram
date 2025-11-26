package com.projectinstagram.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadUserResponse {
    private final Long id;
    private final String nickName;
    private final String name;
    private final String profileImg;
    private final String introduce;
}
