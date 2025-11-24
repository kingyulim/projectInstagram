package com.projectinstagram.domain.like.repository;

import com.projectinstagram.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<Long, CommentLike> {
}
