package com.projectinstagram.domain.board.controller;

import com.projectinstagram.domain.board.dto.*;
import com.projectinstagram.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CreateBoardResponse> create(@RequestPart CreateBoardRequest request,
                                                      @RequestPart List<MultipartFile> files
                                                      ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(files ,request));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ReadBoardResponse> read(@PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.readOneBoard(boardId));
    }

    /** 팔로우 한 사람의 게시물 목록 뿌리기 위해 토큰 필요.*/
    @GetMapping
    public ResponseEntity<List<ReadBoardResponse>> readAll(@RequestParam Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.readAllBoard(id));
    }
    
    @PutMapping
    public ResponseEntity<UpdateBoardResponse> update(@RequestBody Long boardId, UpdateBoardRequest request) {


        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, request)); // 일단 임시로
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<DeleteBoardResponse> delete(@PathVariable Long boardId) {     // 푸시할려고 void로 변경
        DeleteBoardResponse response = boardService.deleteBoard(boardId);

        return ResponseEntity.ok(response);
    }

//    @PostMapping("/upload_image")
//    public ResponseEntity<Void> upload(@RequestPart MultipartFile image, @RequestBody CreateBoardRequest request) {
//        boardService.uploadImages(image, request);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }



}
