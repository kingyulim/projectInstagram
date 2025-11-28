package com.projectinstagram.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadCountResponse {
    private final Long followerCount;
    private final Long followingCount;
}
