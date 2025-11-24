package com.projectinstagram.domain.like.controller;

import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments/{commentId}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    //좋아요 - 댓글
    @PostMapping("/likes")
    public ResponseEntity<CreateResponse> CreateCommentLike(@PathVariable Long commentId, Long userId/*추후 토큰에서 입력하는것으로 변경*/) {
        return ResponseEntity.status(HttpStatus.OK).body(commentLikeService.CreateCommentLike(commentId, userId));
    }


}
