package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.BoardImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createAt = dto.getCreatedAt();
        this.modifiedAt = dto.getModifiedAt();
    }

    public ReadBoardResponse(Long commentCount, Long likeCount,
                             Long id, String nickName, String profileImg,
                             String content, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.content = content;
        this.images = new ArrayList<>();
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

}
