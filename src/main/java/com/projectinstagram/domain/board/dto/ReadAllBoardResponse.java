package com.projectinstagram.domain.board.dto;

import java.time.LocalDateTime;

public interface ReadAllBoardResponse {
    Long getId();
    String getNickname();
    String getProfileImg();
    String getContent();
    String getImage();       // 대표 이미지 파일명
    Long getLikeCount();
    Long getCommentCount();
    LocalDateTime getCreateAt();
    LocalDateTime getModifiedAt();
}

