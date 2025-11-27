package com.projectinstagram.domain.board.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DeleteBoardResponse {

    private Long id;
    private final boolean isSuccess;
    private final String message;

    public DeleteBoardResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public static DeleteBoardResponse of(boolean isSuccess, String message) {
        return new DeleteBoardResponse(isSuccess, message);
    }
}
