package com.projectinstagram.domain.like.controller;

import com.projectinstagram.domain.like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
}
