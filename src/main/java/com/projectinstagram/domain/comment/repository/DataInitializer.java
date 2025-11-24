package com.projectinstagram.domain.comment.repository;

import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.board.entity.Board;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public DataInitializer(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // -----------------------------
        // 1️⃣ 유저 더미 데이터 생성
        // -----------------------------
        if (userRepository.count() == 0) {
            User u1 = new User(
                    "user1@test.com", // email
                    "user1",          // nickname
                    "ㅎㅇ",            // name
                    null,             // profileImage
                    "안녕하세요",       // introduce
                    false,            // isDeletion
                    "1234"            // password
            );

            User u2 = new User(
                    "user2@test.com",
                    "user2",
                    "해윙",
                    null,
                    "안녕하세요",
                    false,
                    "12345"
            );

            User u3 = new User(
                    "user3@test.com",
                    "user3",
                    "할로",
                    null,
                    "안녕하세요",
                    false,
                    "123456"
            );

            userRepository.save(u1);
            userRepository.save(u2);
            userRepository.save(u3);
        }

        // -----------------------------
        // 2️⃣ 게시글 더미 데이터 생성
        // -----------------------------
        if (boardRepository.count() == 0) {
            User writer = userRepository.findAll().get(0); // 첫 번째 유저를 작성자로 사용

            Board b1 = new Board(writer, "내용1");
            Board b2 = new Board(writer, "내용2");

            boardRepository.save(b1);
            boardRepository.save(b2);
        }
    }
}
