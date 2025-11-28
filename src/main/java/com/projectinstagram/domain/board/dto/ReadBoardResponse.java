package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
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
    private List<BoardImage> images;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public ReadBoardResponse(Long id, String nickName, String profileImg, String content, Long likeCount, Long commentCount, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }


    public void setImages(List<BoardImage> images){
        this.images = images;
    }
}
