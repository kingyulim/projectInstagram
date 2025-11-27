package com.projectinstagram.domain.board.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdateBoardRequest {
    private String content;
    private List<MultipartFile> images;
}
