package com.projectinstagram.domain.friend.controller;

import com.projectinstagram.domain.friend.dto.CreateRequest;
import com.projectinstagram.domain.friend.dto.CreateResponse;
import com.projectinstagram.domain.friend.dto.ReadResponse;
import com.projectinstagram.domain.friend.service.FriendService;
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
    public ResponseEntity<CreateResponse> follow (@RequestBody @Valid CreateRequest request, @RequestParam Long userIdFrom/*토큰에서 받는것으로 추후 수정*/) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.follow(request, userIdFrom));
    }

    //친구삭제 (언팔로우)
    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollow (@RequestBody @Valid CreateRequest request, @RequestParam Long userIdFrom/*토큰에서 받는것으로 추후 수정*/) {
        friendService.unfollow(request, userIdFrom);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //팔로워 리스트 조회
    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<ReadResponse>> getFollowerList (@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFollowerList(userId));
    }

}
