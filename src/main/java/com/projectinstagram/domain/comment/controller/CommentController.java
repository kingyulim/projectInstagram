package com.projectinstagram.domain.comment.controller;

import com.projectinstagram.domain.comment.dto.CreateCommentRequest;
import com.projectinstagram.domain.comment.dto.CreateCommentResponse;
import com.projectinstagram.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CreateCommentResponse> saveComment(@PathVariable Long  boardId, @RequestBody CreateCommentRequest request) {

        Long userId = 1L; // 테스트용, DB에 있는 첫 번째 유저



        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(boardId,userId, request));
    }
}
