package com.projectinstagram.domain.board.controller;

import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.dto.CreateBoardResponse;
import com.projectinstagram.domain.board.dto.ReadAllBoardResponse;
import com.projectinstagram.domain.board.dto.ReadOneBoardResponse;
import com.projectinstagram.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CreateBoardResponse> create(@RequestBody CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(null, request));
    }

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<ReadOneBoardResponse> read(@PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.readOneBoard(boardId));
    }

    @PostMapping("/boards")
    public ResponseEntity<ReadAllBoardResponse> readAll(@PathVariable List<String> boardIds) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.readAllBoard(boardIds));
    }


}
