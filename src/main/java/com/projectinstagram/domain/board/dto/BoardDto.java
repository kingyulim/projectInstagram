package com.projectinstagram.domain.board.dto;

import com.projectinstagram.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardDto {
    private Long id;
    private User userId;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardDto(CreateBoardRequest request){

    }
}
