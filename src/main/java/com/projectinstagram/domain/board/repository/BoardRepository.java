package com.projectinstagram.domain.board.repository;

import com.projectinstagram.domain.board.dto.ReadAllBoardResponse;
import com.projectinstagram.domain.board.dto.ReadBoardResponse;
import com.projectinstagram.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    /**
     * Image 리스트 반환 시 에러가 발생하여 이미지는 따로 처리함.
     */
    // 게시물 단건 조회 쿼리메서드.
    @Query("""
            SELECT new com.projectinstagram.domain.board.dto.ReadBoardResponse(
                        b.id, u.nickname, u.profileImage, b.content,
                        COUNT(DISTINCT bl), COUNT(DISTINCT c),
                        b.createdAt, b.modifiedAt)
                        FROM Board b
                        JOIN b.userId u
                        LEFT JOIN b.likes bl
                        LEFT JOIN b.comments c
                        WHERE b.id = :boardId
                        GROUP BY b.id, u.nickname, u.profileImage, b.content, b.createdAt, b.modifiedAt""")
    ReadBoardResponse findBoardDetail(@Param("boardId") Long boardId);

    //페이지 조회. NativeSQL은 클래스 기반 DTO 생성자를 지원하지 않아서 인터페이스 Projection을 써야 함.
    @Query(value = """
            SELECT b.id AS id, u.nickname AS nickName, u.profile_image AS profileImg, b.content AS content,
            (SELECT bi.file_name FROM board_images bi WHERE bi.board_id = b.id ORDER BY bi.id ASC LIMIT 1) AS image,
            COUNT(DISTINCT bl.user_id) AS likeCount,
            COUNT(DISTINCT c.id) AS commentCount,
            b.created_at AS createAt, b.modified_at AS modifiedAt
            FROM boards b
            JOIN users u ON b.user_id = u.id
            JOIN friends f ON f.user_id_to = u.id
            LEFT JOIN board_likes bl ON bl.board_id = b.id
            LEFT JOIN comments c ON c.board_id = b.id
            WHERE f.user_id_from = :userId
            GROUP BY b.id, u.nickname, u.profile_image, b.content, b.created_at, b.modified_at
            ORDER BY b.created_at DESC
            """,
            nativeQuery = true)
    Page<ReadAllBoardResponse> findAllBoardDetails(@Param("userId") Long userId, Pageable pageable);
}
