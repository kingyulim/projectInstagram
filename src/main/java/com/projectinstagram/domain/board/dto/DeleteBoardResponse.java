package com.projectinstagram.domain.board.dto;

import lombok.Getter;


@Getter
public class DeleteBoardResponse {
    private final String message;

    public DeleteBoardResponse(String message) {
        this.message = message;
    }
}
