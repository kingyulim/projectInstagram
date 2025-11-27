package com.projectinstagram.domain.friend.controller;

import com.projectinstagram.domain.friend.dto.*;
import com.projectinstagram.domain.friend.service.FriendService;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FriendController {
    private final FriendService friendService;

    //친구추가 (팔로우)
    @PostMapping
    public ResponseEntity<CreateResponse> follow (@RequestBody @Valid CreateRequest request, HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        Long userIdFrom = thisToken.getId();
        return ResponseEntity.status(HttpStatus.OK).body(friendService.follow(request, userIdFrom));
    }

    //친구삭제 (언팔로우)
    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollow (@RequestBody @Valid CreateRequest request, HttpServletRequest servletRequest) {
        TokenResponse thisToken = (TokenResponse) servletRequest.getAttribute("thisToken");
        Long userIdFrom = thisToken.getId();
        friendService.unfollow(request, userIdFrom);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //팔로워 리스트 조회
    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<ReadUserResponse>> getFollowerList (@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFollowerList(userId));
    }

    //팔로잉 리스트 조회
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<ReadUserResponse>> getFollowingList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFollowingList(userId));
    }

    //팔로워,팔로잉 수 조회
    @GetMapping("/count/{userId}")
    public ResponseEntity<ReadCountResponse> getFollowCount(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFollowCount(userId));
    }

    //팔로워,팔로잉 수 및 리스트 조회
    @GetMapping("/list/{userId}")
    public ResponseEntity<ReadCountAndUserResponse> getFollowCountList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFollowCountList(userId));
    }

}
