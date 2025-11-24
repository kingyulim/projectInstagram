package com.projectinstagram.domain.like.repository;

import com.projectinstagram.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//테스트용 - 푸쉬 전 삭제 필요
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
