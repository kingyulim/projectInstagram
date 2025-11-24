package com.projectinstagram.domain.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateBoardRequest {
    private String content;
    private List<String> contentImg;

}
