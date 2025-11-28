package com.projectinstagram.domain.board.repository;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findAllByBoardId(Board board);
}
