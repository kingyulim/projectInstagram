package com.projectinstagram.domain.board.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class BoardLikeId {
    private Long boardId;
    private Long userId;
}
