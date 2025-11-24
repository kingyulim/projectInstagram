package com.projectinstagram.domain.board.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.dto.CreateBoardResponse;
import com.projectinstagram.domain.board.dto.ReadOneBoardResponse;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ReadOneBoardResponse readOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION)
        );

        return new ReadOneBoardResponse(board, null);  // 더미데이터로 임시처리
    }
}
