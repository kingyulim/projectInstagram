package com.projectinstagram.domain.board.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.dto.*;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public CreateBoardResponse createBoard(User user, CreateBoardRequest request){
        return CreateBoardResponse.from(boardRepository.save(new Board(user, request)));
    }

    @Transactional(readOnly = true)
    public ReadOneBoardResponse readOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION)
        );

        return new ReadOneBoardResponse(board, null);  // 더미데이터로 임시처리
    }

    @Transactional(readOnly = true)
    public ReadAllBoardResponse readAllBoard(List<String> boardIds) {
        Board board = boardRepository.findById(boardIds)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
    }

//    @Transactional
//    public UpdateBoardResponse update()

}
