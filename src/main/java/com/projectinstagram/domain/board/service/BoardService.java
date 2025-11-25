package com.projectinstagram.domain.board.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.dto.*;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.entity.BoardImage;
import com.projectinstagram.domain.board.repository.BoardImageRepository;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository imageRepository;
    private final String uploadRootDir = "board_images";

    public CreateBoardResponse createBoard(User user, CreateBoardRequest request){
        Board board = boardRepository.save(Board.from(null, request));

        return new CreateBoardResponse(BoardDto.from(board));
    }

    public ReadBoardResponse readOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
        return new ReadBoardResponse(BoardDto.from(board));  // 더미데이터로 임시처리
    }

    @Transactional(readOnly = true)
    public List<ReadBoardResponse> readAllBoard(Long id) {
        List<Board> boards = boardRepository.findAllByIdOrderByModifiedAtDesc(id);
        List<ReadBoardResponse> result = new ArrayList<>();

        for (Board board: boards){
            result.add(new ReadBoardResponse(BoardDto.from(board)));
        }
        return result;
    }

//    @Transactional
//    public UpdateBoardResponse update()

    public void uploadImages(MultipartFile image, CreateBoardRequest request){
        Board board = boardRepository.save(Board.from(null, request));

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadRootDir, fileName);
        try{
            Files.createDirectories(filePath.getParent());
            image.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
        BoardImage boardImage = new BoardImage(board, "/uploads/" + fileName);

        imageRepository.save(boardImage);

    }
}