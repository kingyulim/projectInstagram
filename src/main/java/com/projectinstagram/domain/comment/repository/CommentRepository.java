package com.projectinstagram.domain.comment.repository;


import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Board board);
}
