package com.projectinstagram.domain.user.entity;

import com.projectinstagram.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(columnDefinition = "text")
    private String introduce;

    @Column(length = 250)
    private String profileImage;

    @Column(nullable = false)
    private Boolean isDeletion = false;

    public User(
            String email,
            String name,
            String password,
            String nickname
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;

    }
}
