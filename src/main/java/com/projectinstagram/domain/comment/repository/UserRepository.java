package com.projectinstagram.domain.comment.repository;

import com.projectinstagram.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
