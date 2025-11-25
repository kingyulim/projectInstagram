package com.projectinstagram.domain.like.deletion;

import com.projectinstagram.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//테스트용 - 푸쉬 전 삭제 필요
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
