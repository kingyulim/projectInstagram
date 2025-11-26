package com.projectinstagram.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadCountAndUserResponse {
    private final Long followerCount;
    private final Long followingCount;
    private final List<ReadUserResponse> followerList;
    private final List<ReadUserResponse> followingList;
}
