package com.projectinstagram.domain.like.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.like.dto.CreateResponse;
import com.projectinstagram.domain.like.entity.CommentLike;
import com.projectinstagram.domain.like.entity.CommentLikeId;
import com.projectinstagram.domain.like.repository.CommentLikeRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.projectinstagram.common.exception.ExceptionMessageEnum.NO_MEMBER_ID;
import static com.projectinstagram.common.exception.ExceptionMessageEnum.SELF_LIKE_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //댓글 좋아요 생성,조회
    public CreateResponse CreateCommentLike(Long commentId, Long userId/*필터로부터 받은 값(수정필요)*/) {

        if (commentId == null) {throw new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION);} /*COMMENT_NOT_FOUND_EXCEPTION로 연동 필요*/
        if (userId == null) {throw new CustomException(NO_MEMBER_ID);}
        CommentLikeId commentLikeId = new CommentLikeId(commentId, userId);
        boolean isLiked = commentLikeRepository.existsById(commentLikeId); //좋아요가 이미 눌려있는지 확인

        if (isLiked == true) {
            commentLikeRepository.deleteById(commentLikeId); //좋아요가 이미 눌려있으면 좋아요 취소
        } else {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionMessageEnum.COMMENT_NOT_FOUND_EXCEPTION)); /*COMMENT_NOT_FOUND_EXCEPTION로 변경필요*/
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NO_MEMBER_ID));
            if (comment.getUserId().getId().equals(userId)) { //본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없는 기능 /*병합시 comment 엔티티의 user필드 명칭 변경되면 에러*/
             throw new CustomException(SELF_LIKE_EXCEPTION); /*에러 추가: 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.*/
            } else {
                CommentLike commentLike = new CommentLike(comment, user);
                commentLikeRepository.save(commentLike); //좋아요가 안눌려있으면 좋아요 생성
            }
        }

        Long likeCount = commentLikeRepository.countByComment_Id(commentId); //선택된 댓글의 좋아요 수 반환

        return new CreateResponse(likeCount);
    }
}
