package com.projectinstagram.domain.comment.repository;


import com.projectinstagram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
