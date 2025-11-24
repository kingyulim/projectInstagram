package com.projectinstagram.domain.user.entity;

import com.projectinstagram.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="users")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String profileImage;
    private String introduce;
    private Boolean isDeletion;
    private String password;


    // User.java
    public User(String email, String nickname, String name, String profileImage, String introduce, Boolean isDeletion, String password) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.isDeletion = isDeletion;
        this.password = password;
    }


}
