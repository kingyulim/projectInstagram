package com.projectinstagram.domain.comment.service;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.comment.dto.*;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public CreateCommentResponse save(Long boardId, Long userId, CreateCommentRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Comment comment = new Comment(board, user, request.getContent());
        commentRepository.save(comment);

        return new CreateCommentResponse(comment.getId(), comment.getUserId().getId(), comment.getBoardId().getId(), comment.getContent(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        List<Comment> comments = commentRepository.findByBoardId(board);
        return comments.stream()
                .map(c -> new GetCommentResponse(
                        c.getId(),
                        c.getUserId().getId(),
                        c.getBoardId().getId(),
                        c.getContent(),
                        c.getCreatedAt(),
                        c.getModifiedAt()
                ))
                .toList();
    }

    // 댓글수정
    @Transactional
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));
        comment.update(request.getContent());
        return new UpdateCommentResponse(comment.getId(), comment.getUserId().getId(), comment.getBoardId().getId(), comment.getContent(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    @Transactional
    public void delete(Long commentId) {
        boolean existence = commentRepository.existsById(commentId);
        if (!existence) {
            throw new IllegalStateException("없는 댓글입니다");
        }
        commentRepository.deleteById(commentId);
    }

}
