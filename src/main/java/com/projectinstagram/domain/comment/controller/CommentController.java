package com.projectinstagram.domain.comment.controller;

import com.projectinstagram.domain.comment.dto.*;
import com.projectinstagram.domain.comment.service.CommentService;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성하기
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CreateCommentResponse> saveComment(@PathVariable Long  boardId,  @Valid @RequestBody CreateCommentRequest request, HttpServletRequest servletRequest) {

        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(boardId,thisToken.getId(), request));
    }

    // 댓글 조회하기
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(@PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(boardId));

    }

    // 댓글 수정하기
    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request, HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");

        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, thisToken.getId(),  request));
    }

    // 댓글 삭제하기
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest servletRequest
    ) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        commentService.delete(commentId, thisToken.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
