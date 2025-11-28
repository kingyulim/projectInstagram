package com.projectinstagram.common.util;

import com.projectinstagram.domain.board.dto.CreateBoardRequest;
import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.board.repository.BoardRepository;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.comment.repository.CommentRepository;
import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.repository.FriendRepository;
import com.projectinstagram.domain.like.entity.BoardLike;
import com.projectinstagram.domain.like.entity.CommentLike;
import com.projectinstagram.domain.like.repository.BoardLikeRepository;
import com.projectinstagram.domain.like.repository.CommentLikeRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import com.projectinstagram.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 유저 준비
        User user1 = new User("test@email.com","김테스트", passwordEncoder.encode("1234"), "test_id");
        User user2 = new User("test2@email.com","김테스트2", passwordEncoder.encode("1234"),"test_id2");
        User user3 = new User("test3@email.com","김테스트3", passwordEncoder.encode("1234"),"test_id3");
        User user4 = new User("test4@email.com","김테스트4", passwordEncoder.encode("1234"),"test_id4");
        User user5 = new User("test5@email.com","김테스트5", passwordEncoder.encode("1234"), "test_id5");
        // 유저 생성
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);


        //친구관계 준비
        Friend Friend12 = new Friend(user1, user2);
        Friend Friend13 = new Friend(user1, user3);
        Friend Friend14 = new Friend(user1, user4);
        Friend Friend15 = new Friend(user1, user5);
        Friend Friend32 = new Friend(user3, user2);
        Friend Friend42 = new Friend(user4, user2);
        Friend Friend52 = new Friend(user5, user2);

        //친구관계 생성
        friendRepository.save(Friend12);
        friendRepository.save(Friend13);
        friendRepository.save(Friend14);
        friendRepository.save(Friend15);
        friendRepository.save(Friend32);
        friendRepository.save(Friend42);
        friendRepository.save(Friend52);


        //게시물 준비
        CreateBoardRequest boardDto1 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물1 내용1";
            }
        };
        CreateBoardRequest boardDto2 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물2 내용2";
            }
        };
        CreateBoardRequest boardDto3 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물3 내용3";
            }
        };
        CreateBoardRequest boardDto4 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물4 내용4";
            }
        };
        CreateBoardRequest boardDto5 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물5 내용5";
            }
        };
        CreateBoardRequest boardDto6 = new CreateBoardRequest() {
            @Override
            public String getContent() {
                return "게시물6 내용6";
            }
        };

        Board board1 = new Board(user1, boardDto1);
        Board board2 = new Board(user2, boardDto2);
        Board board3 = new Board(user3, boardDto3);
        Board board4 = new Board(user1, boardDto4);
        Board board5 = new Board(user1, boardDto5);
        Board board6 = new Board(user1, boardDto6);

        //게시물 생성
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
        boardRepository.save(board5);
        boardRepository.save(board6);

        //게시물 좋아요
        BoardLike boardLike2 = new BoardLike(board1, user2);
        BoardLike boardLike3 = new BoardLike(board1, user3);
        //게시물 좋아요생성
        boardLikeRepository.save(boardLike2);
        boardLikeRepository.save(boardLike3);

        //댓글 준비
        Comment comment1 = new Comment(board1, user1, "댓글");
        Comment comment2 = new Comment(board1, user1, "댓글");
        Comment comment3 = new Comment(board1, user1, "댓글");
        Comment comment4 = new Comment(board1, user1, "댓글");

        //댓글 생성
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

        //댓글 좋아요
        CommentLike commentLike2 = new CommentLike(comment1, user2);
        CommentLike commentLike3 = new CommentLike(comment1, user3);
        CommentLike commentLike4 = new CommentLike(comment1, user4);

        //댓글 좋아요 생성
        commentLikeRepository.save(commentLike2);
        commentLikeRepository.save(commentLike3);
        commentLikeRepository.save(commentLike4);
    }
}