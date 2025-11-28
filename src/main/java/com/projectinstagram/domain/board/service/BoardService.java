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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public CreateBoardResponse createBoard(Long id, CreateBoardRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO));
        List<String>imageUrls = imageService.storeAll(ImageUrl.BOARD_URL,request.getImages());
        Board board = boardRepository.save(Board.from(user, request));
        if (imageUrls != null) {
            for (String imageUrl : imageUrls) {
                board.setImages(new BoardImage(board, imageUrl));
            }
        }
        return new CreateBoardResponse(BoardDto.from(board));
    }

    // 게시물 단건 조회
    @Transactional(readOnly = true)
    public ReadBoardResponse readOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
        List<BoardImage> images = boardImageRepository.findAllByBoardId(board);
        ReadBoardResponse response = boardRepository.findBoardDetail(boardId);
        response.setImages(images);
        return response;
    }

    // 팔로우 목록에 있는 유저 게시물들 생성일 기준 내림차순 조회
    @Transactional(readOnly = true)
    public Page<ReadAllBoardResponse> readAllBoard(Long id, int page, String sort) {
        if (!(sort.equals("likeCount")||sort.equals("modified_at"))) sort="created_at";
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC, sort));
        return boardRepository.findAllBoardDetails(id, pageable);
    }

    public UpdateBoardResponse updateBoard(Long tokenId, Long boardId, UpdateBoardRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));

        if (!board.getUserId().getId().equals(tokenId)) {
            throw new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO);
        }

        if (request.getContent() == null) throw new CustomException(ExceptionMessageEnum.NULL_POINT_EXCEPTION);
        board.setContent(request);
        boardRepository.flush();
        return new UpdateBoardResponse(BoardDto.from(board));
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