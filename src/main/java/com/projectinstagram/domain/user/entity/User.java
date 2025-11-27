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

    /**
     * 회원 수정 메서드
     * @param email
     * @param nickname
     * @param name
     * @param introduce
     * @param profileImage
     */
    public void userModified(
            String email,
            String nickname,
            String name,
            String introduce,
            String profileImage
    ) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.introduce = introduce;
        this.profileImage = profileImage;
    }

    /**
     * 비밀번호 변경 메서드
     * @param newPassword 새로운 비밀번호
     */
    public void passwordChange(String newPassword) {
        this.password = newPassword;
    }

    /**
     * 탈퇴회원 메서드
     * @param isDeletion 탈퇴 상태 파라미터
     */
    public void userDelete(Boolean isDeletion) {
        this.isDeletion = isDeletion;
    }
}
