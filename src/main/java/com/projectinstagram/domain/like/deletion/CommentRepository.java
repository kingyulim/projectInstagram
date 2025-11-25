package com.projectinstagram.domain.like.deletion;

import com.projectinstagram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//테스트용 - 푸쉬 전 삭제 필요
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
