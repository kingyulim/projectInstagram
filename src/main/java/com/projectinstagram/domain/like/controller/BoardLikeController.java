package com.projectinstagram.domain.like.controller;

import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.service.BoardLikeService;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes/boards/{boardId}")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    //게시물 좋아요 생성,조회
    @PostMapping
    public ResponseEntity<CreateResponse> CreateBoardLike(@PathVariable Long boardId, HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        Long userId = thisToken.getId();
        return ResponseEntity.status(HttpStatus.OK).body(boardLikeService.CreateBoardLike(boardId, userId));
    }

}
