package com.projectinstagram.domain.like.controller;

import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.service.CommentLikeService;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes/boards/{boardId}/comments/{commentId}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    //댓글 좋아요 생성,조회
    @PostMapping
    public ResponseEntity<CreateResponse> CreateCommentLike(@PathVariable Long commentId, HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        Long userId = thisToken.getId();
        return ResponseEntity.status(HttpStatus.OK).body(commentLikeService.CreateCommentLike(commentId, userId));
    }


}
