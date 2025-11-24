package com.projectinstagram.domain.board.service;

import com.projectinstagram.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public CreateBoardResponse createBoard(CreateBoardRequest request){

    }
}
