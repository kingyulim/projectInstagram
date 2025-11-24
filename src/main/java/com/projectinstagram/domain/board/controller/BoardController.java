package com.projectinstagram.domain.board.controller;

import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.dto.CreateBoardResponse;
import com.projectinstagram.domain.board.dto.ReadOneBoardResponse;
import com.projectinstagram.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CreateBoardResponse> create(@RequestBody CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(null, request));
    }

//    @PostMapping("/boards/{boardId}")
//    public ResponseEntity<ReadOneBoardResponse>

}
