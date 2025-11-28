package com.projectinstagram.domain.user.repository;

import com.projectinstagram.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 같은 이메일이 존재하는지 검사
     * @param email request eamil 파라미터
     * @return SELECT 1 FROM users WHERE email = ?
     */
    boolean existsByEmail(String email);

    /**
     * 같은 닉네임이 존재하는지 검사
     * @param nickname request nickname 파라미터
     * @return SELECT 1 FROM users WHERE nickname = ?
     */
    boolean existsByNickname(String nickname);

    /**
     * email 데이터가 존재한다면 그에대한 모든 필드 값 반환
     * @param email request eamil 파라미터
     * @return SELECT * FROM users WHERE email = ?
     */
    Optional<User> findByEmail(String email);

}
