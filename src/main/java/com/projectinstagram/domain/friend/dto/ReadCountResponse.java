package com.projectinstagram.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadCountResponse {
    private Long followerCount;
    private Long followingCount;
}
