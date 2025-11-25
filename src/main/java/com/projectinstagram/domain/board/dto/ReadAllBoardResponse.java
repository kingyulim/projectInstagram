package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadAllBoardResponse {

    private Long id;
    private String nickName;
    private String profileImg;
    private List<String> contentImg;
    private String content;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ReadAllBoardResponse(Board board, User user) {
        this.id = board.getId();
        this.nickName = user.getNickname();
        this.profileImg = user.getProfileImage();
        this.contentImg = board.getContentImg();
        this.content = board.getContent();
        this.likeCount = 1;
        this.commentCount = 1;
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public static ReadAllBoardResponse from(Board board, User user) {
        return new ReadAllBoardResponse(board, user);
    }
}
