package com.projectinstagram.domain.like.repository;

import com.projectinstagram.domain.like.entity.BoardLike;
import com.projectinstagram.domain.like.entity.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {
    long countByBoard_Id(Long boardId);
}
