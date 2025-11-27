package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.BoardImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadBoardResponse {

    private final Long id;
    private final String nickName;
    private final String profileImg;
    private final String content;
    private final List<BoardImage> images;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public ReadBoardResponse(Long commentCount, Long likeCount, BoardDto dto) {
        this.id = dto.getId();
        this.nickName = dto.getUserId().getNickname();
        this.profileImg = dto.getUserId().getProfileImage();
        this.content = dto.getContent();
        this.images = dto.getContentImg();
        this.likeCount = likeCount; // 더미데이터
        this.commentCount = commentCount; // 더미데이터
        this.createAt = dto.getCreatedAt();
        this.modifiedAt = dto.getModifiedAt();
    }
}
