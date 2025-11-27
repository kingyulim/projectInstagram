package com.projectinstagram.domain.like.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.entity.BoardLike;
import com.projectinstagram.domain.like.entity.BoardLikeId;
import com.projectinstagram.domain.like.repository.BoardLikeRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시물 좋아요 생성,조회
    public CreateResponse CreateBoardLike(Long boardId, Long userId/*필터로부터 받은 값(수정필요)*/) {

        if (boardId == null) {throw new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION);}
        if (userId == null) {throw new CustomException(ExceptionMessageEnum.NO_MEMBER_ID);}
        BoardLikeId boardLikeId = new BoardLikeId(boardId, userId);
        boolean isLiked = boardLikeRepository.existsById(boardLikeId); //좋아요가 이미 눌려있는지 확인

        if (isLiked == true) {
            boardLikeRepository.deleteById(boardLikeId); //좋아요가 이미 눌려있으면 좋아요 취소
        } else {
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ExceptionMessageEnum.BOARD_NOT_FOUND_EXCEPTION));
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID));
            if (board.getUserId().getId().equals(userId)) { //본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없는 기능 /*병합시 board 엔티티의 user필드 명칭 변경되면 에러*/
                throw new CustomException(ExceptionMessageEnum.SELF_LIKE_EXCEPTION); /*에러 추가: 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.*/
            } else {
                BoardLike boardLike = new BoardLike(board, user);
                boardLikeRepository.save(boardLike); //좋아요가 안눌려있으면 좋아요 생성
            }
        }

        Long likeCount = boardLikeRepository.countByBoard_Id(boardId); //선택된 댓글의 좋아요 수 반환

        return new CreateResponse(likeCount);
    }

}
