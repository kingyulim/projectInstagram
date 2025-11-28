package com.projectinstagram.domain.like.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeId implements Serializable {
    @Column(name = "board_id")
    private Long boardId;
    @Column(name = "user_id")
    private Long userId;
}
