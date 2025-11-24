package com.projectinstagram.domain.user.controller;

import com.projectinstagram.domain.user.dto.request.UserJoinRequestDto;
import com.projectinstagram.domain.user.dto.response.UserJoinResponseDto;
import com.projectinstagram.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponseDto> join(@Valid @RequestBody UserJoinRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.join(request));
    }
}
