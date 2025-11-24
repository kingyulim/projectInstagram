package com.projectinstagram.domain.board.service;

import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.dto.CreateBoardResponse;
import com.projectinstagram.domain.board.dto.ReadOneBoardResponse;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public CreateBoardResponse createBoard(User user, CreateBoardRequest request){
        Board board = new Board(user, request);
        Board result = boardRepository.save(board);
        return CreateBoardResponse.from(result);
    }

    @Transactional
    public ReadOneBoardResponse readOneBoard(ReadOneBoardResponse response) {

    }
}
