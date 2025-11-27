package com.projectinstagram.domain.board.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.common.util.ImageService;
import com.projectinstagram.common.util.ImageUrl;
import com.projectinstagram.domain.board.dto.*;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.entity.BoardImage;
import com.projectinstagram.domain.board.repository.BoardImageRepository;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public CreateBoardResponse createBoard(Long id, CreateBoardRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO));
        List<String>imageUrls = imageService.storeAll(ImageUrl.BOARD_URL,request.getImages());
        Board board = Board.from(user, request);
        List<BoardImage> imageList = new ArrayList<>();
        for (String imageUrl: imageUrls) {
            board.setImages(new BoardImage(imageUrl));
        }
        return new CreateBoardResponse(BoardDto.from(boardRepository.save(board)));
    }

    @Transactional(readOnly = true)
    public ReadBoardResponse readOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
        return new ReadBoardResponse(BoardDto.from(board));  // 더미데이터로 임시처리
    }

    @Transactional(readOnly = true)
    public List<ReadBoardResponse> readAllBoard(Long id) {
        List<Board> boards = boardRepository.findAllByIdOrderByModifiedAtDesc(id);
        List<ReadBoardResponse> result = new ArrayList<>();

        for (Board board : boards) {
            result.add(new ReadBoardResponse(BoardDto.from(board)));
        }
        return result;
    }

    public UpdateBoardResponse updateBoard(Long tokenId, Long boardId, UpdateBoardRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));


        // 후에 로그인 기능 추가되면 userId랑 비교 조건 추가할 예정
        if (board.getUserId().getId().equals(tokenId)) {
            new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO);
        }
        board.setContent(request);
        return UpdateBoardResponse.from(BoardDto.from(board));
    }

    public DeleteBoardResponse deleteBoard(Long tokenId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
        if (board.getUserId().getId().equals(tokenId)) {
            new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO);
        }
        boardRepository.delete(board);

        return new DeleteBoardResponse("게시물이 삭제되었습니다");
    }
}