package com.projectinstagram.domain.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateBoardRequest {
    private String content;
    private List<String> contentImg;
}
