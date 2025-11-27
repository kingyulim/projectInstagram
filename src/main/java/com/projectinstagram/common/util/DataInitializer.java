package com.projectinstagram.common.util;

import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.repository.FriendRepository;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/*데이터더미 세팅 후 테스트, 추후 제거*/
@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public void run(String... args) throws Exception {

        // 유저 준비
        User user1 = new User("test@email.com", "test_id", "김테스트","1234");
        User user2 = new User("test@email.com2", "test_id2", "김테스트2","1234");
        User user3 = new User("test@email.com3", "test_id3", "김테스트3","1234");
        User user4 = new User("test@email.com4", "test_id4", "김테스트4","1234");
        User user5 = new User("test@email.com5", "test_id5", "김테스트5","1234");


        //친구관계 준비
        Friend Friend12 = new Friend(user1, user2);
        Friend Friend13 = new Friend(user1, user3);
        Friend Friend14 = new Friend(user1, user4);
        Friend Friend15 = new Friend(user1, user5);
        Friend Friend32 = new Friend(user3, user2);
        Friend Friend42 = new Friend(user4, user2);
        Friend Friend52 = new Friend(user5, user2);
        //팔로잉 준비

//       public User (String email, String nickname, String name, String password) {
//            this.email = email;
//            this.nickname = nickname;
//            this.name = name;
//            this.password = password;
//        }


        // 유저 생성
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        //친구관계 생성
        friendRepository.save(Friend12);
        friendRepository.save(Friend13);
        friendRepository.save(Friend14);
        friendRepository.save(Friend15);
        friendRepository.save(Friend32);
        friendRepository.save(Friend42);
        friendRepository.save(Friend52);

    }
}