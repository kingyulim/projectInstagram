package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.user.entity.User;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadOneBoardResponse {

    private Long id;
    private String nickName;
    private String profileImg;
    private List<String> contentImg;
    private String content;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReadOneBoardResponse(Board board, User user) {
        this.id = board.getId();
        this.nickName = user.getNickname();
        this.profileImg = user.getProfileImage();
        this.contentImg = board.getContentImg();
        this.content = board.getContent();
        this.likeCount = 1; // 더미데이터
        this.commentCount = 1; // 더미데이터
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
