package com.projectinstagram.domain.friend.deletion;

import com.projectinstagram.domain.board.entity.Board;
import com.projectinstagram.domain.comment.entity.Comment;
import com.projectinstagram.domain.friend.deletion.UserRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*데이터더미 세팅 후 테스트, 추후 제거*/
@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
/*
        // 유저 준비
        User user1 = new User("test@email.com", "test_id", "김테스트","1234");
        User user2 = new User("test@email.com2", "test_id2", "김테스트2","1234");

//       public User (String email, String nickname, String name, String password) {
//            this.email = email;
//            this.nickname = nickname;
//            this.name = name;
//            this.password = password;
//        }


        // 유저 생성
        userRepository.save(user1);
        userRepository.save(user2);
*/

    }
}