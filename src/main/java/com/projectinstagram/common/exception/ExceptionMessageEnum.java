package com.projectinstagram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageEnum {
    NO_MEMBER_ID(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다."),
    NO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 되지 않았습니다."),
    BOARD_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "해당 게시물은 존재하지 않습니다."),
    POST_ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "접근 불가능한 게시물입니다."),
    SELF_FOLLOW_EXCEPTION(HttpStatus.UNAUTHORIZED, "스스로에게 팔로우, 언팔로우 할 수 없습니다."),
    ALREADY_FRIEND_EXCEPTION(HttpStatus.UNAUTHORIZED, "해당 유저는 이미 친구입니다."),
    NOT_FRIEND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 유저는 친구가 아닙니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
