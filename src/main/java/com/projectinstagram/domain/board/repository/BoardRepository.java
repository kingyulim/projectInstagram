package com.projectinstagram.domain.board.repository;

import com.projectinstagram.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByIdOrderByModifiedAtDesc(Long id);
}
