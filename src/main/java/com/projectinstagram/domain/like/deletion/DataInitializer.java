package com.projectinstagram.domain.like.deletion;

import com.projectinstagram.domain.like.repository.CommentLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*데이터더미 세팅 후 테스트, 추후 제거*/
@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final BoardRepository boardRepository;


    @Override
    public void run(String... args) throws Exception {
       /* // 유저 준비
        User user1 = new User("test@email.com", "test_id", "김테스트","1234");

//       public User (String email, String nickname, String name, String password) {
//            this.email = email;
//            this.nickname = nickname;
//            this.name = name;
//            this.password = password;
//        }

        // 게시물 준비
        Board board1 = new Board(user1, "내용");

//            public Board (User userId, String content){
//            this.userId = userId;
//            this.content = content;
//        }

        // 댓글 준비
        Comment comment1 = new Comment(board1, user1);

//    public Comment (Board boardId, User userId) {
//            this.boardId = boardId;
//            this.userId = userId;
//        }

        // 유저 생성
        userRepository.save(user1);
        // 일정 생성
        boardRepository.save(board1);
        // 댓글 생성
        commentRepository.save(comment1);*/
    }
}
