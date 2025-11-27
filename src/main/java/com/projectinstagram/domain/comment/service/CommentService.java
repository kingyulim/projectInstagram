package com.projectinstagram.domain.comment.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.comment.dto.*;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.like.repository.CommentLikeRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 댓글 작성
    @Transactional
    public CreateCommentResponse save(Long boardId, Long userId, CreateCommentRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID));


        Comment comment = new Comment(board, user, request.getContent());
        commentRepository.save(comment);

        return new CreateCommentResponse(comment.getId(), comment.getUserId().getId(), comment.getBoardId().getId(), comment.getContent(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));

        List<Comment> comments = commentRepository.findByBoardId(board);
        return comments.stream()
                .map(c -> {
                    Long likeCount = commentLikeRepository.countByComment_Id(c.getId());
                    return new GetCommentResponse(
                            c.getId(),
                            c.getUserId().getId(),
                            c.getBoardId().getId(),
                            c.getContent(),
                            likeCount,
                            c.getCreatedAt(),
                            c.getModifiedAt()
                    );
                })
                .toList();
    }

    // 댓글수정
    @Transactional
    public UpdateCommentResponse update(Long commentId, Long userId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION));

        if (!comment.getUserId().getId().equals(userId)) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }
        comment.update(request.getContent());
        Long likeCount = commentLikeRepository.countByComment_Id(comment.getId());

        return new UpdateCommentResponse(
                comment.getId(),
                comment.getUserId().getId(),
                comment.getBoardId().getId(),
                comment.getContent(),
                likeCount,
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION));

        // 본인 확인
        if (!comment.getUserId().getId().equals(userId)) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        commentRepository.delete(comment);
    }


}
