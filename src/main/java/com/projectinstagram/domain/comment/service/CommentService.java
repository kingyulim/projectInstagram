package com.projectinstagram.domain.comment.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.comment.dto.*;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.user.entity.User;
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
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));//new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION));
        comment.update(request.getContent());
        return new UpdateCommentResponse(comment.getId(), comment.getUserId().getId(), comment.getBoardId().getId(), comment.getContent(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    @Transactional
    public void delete(Long commentId) {
        boolean existence = commentRepository.existsById(commentId);
        if (!existence) {
            throw new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION);//new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION);
        }
        commentRepository.deleteById(commentId);
    }

    //COMMENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    //COMMENT_ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "댓글 수정/삭제 권한이 없습니다.")
}
