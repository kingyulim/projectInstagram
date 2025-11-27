package com.projectinstagram.domain.board.controller;

import com.projectinstagram.domain.board.dto.*;
import com.projectinstagram.domain.board.service.BoardService;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateBoardResponse> create(@ModelAttribute CreateBoardRequest request,
                                                      HttpServletRequest servletRequest
                                                      ) {

        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");

        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(thisToken.getId(), request));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ReadBoardResponse> read(HttpServletRequest servletRequest, @PathVariable Long boardId) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");

        return ResponseEntity.status(HttpStatus.OK).body(boardService.readOneBoard(thisToken.getId(), boardId));
    }

    /** 팔로우 한 사람의 게시물 목록 뿌리기 위해 토큰 필요.*/
    @GetMapping
    public ResponseEntity<List<ReadBoardResponse>> readAll(HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        return ResponseEntity.status(HttpStatus.OK).body(boardService.readAllBoard(thisToken.getId()));
    }
    
    @PutMapping("/{boardId}")
    public ResponseEntity<UpdateBoardResponse> update(HttpServletRequest servletRequest,Long boardId, UpdateBoardRequest request) {

        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(thisToken.getId(), boardId, request)); // 일단 임시로
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<DeleteBoardResponse> delete(HttpServletRequest servletRequest, @PathVariable Long boardId) {     // 푸시할려고 void로 변경
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(thisToken.getId(), boardId));
    }

//    @PostMapping("/upload_image")
//    public ResponseEntity<Void> upload(@RequestPart MultipartFile image, @RequestBody CreateBoardRequest request) {
//        boardService.uploadImages(image, request);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }



}
