package com.projectinstagram.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CreateBoardRequest {
    private List<MultipartFile> images;
    private String content;
}
