package com.projectinstagram.domain.like.controller;

import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    //게시물 좋아요 생성,조회
    @PostMapping("/likes")
    public ResponseEntity<CreateResponse> CreateBoardLike(@PathVariable Long boardId, @RequestParam Long userId/*추후 토큰에서 입력하는것으로 변경*/) {
        return ResponseEntity.status(HttpStatus.OK).body(boardLikeService.CreateBoardLike(boardId, userId));
    }

}
