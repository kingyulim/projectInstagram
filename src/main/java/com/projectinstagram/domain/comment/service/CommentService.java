package com.projectinstagram.domain.comment.service;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.comment.dto.CreateCommentRequest;
import com.projectinstagram.domain.comment.dto.CreateCommentResponse;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.BoardRepository;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.comment.repository.UserRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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

}
