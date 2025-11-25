package com.projectinstagram.domain.friend.controller;

import com.projectinstagram.domain.friend.dto.CreateRequest;
import com.projectinstagram.domain.friend.dto.CreateResponse;
import com.projectinstagram.domain.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FriendController {
    private final FriendService friendService;

    //친구추가 (팔로우)
    @PostMapping
    public ResponseEntity<CreateResponse> follow(@RequestBody @Valid CreateRequest request, @RequestParam Long userIdFrom/*토큰에서 받는것으로 추후 수정*/) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.createResponse(request, userIdFrom));
    }

}
