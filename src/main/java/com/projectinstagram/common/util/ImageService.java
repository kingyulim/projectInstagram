package com.projectinstagram.common.util;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    //단건 저장.
    public String store(ImageUrl url, MultipartFile file) {
        String fileName = generateFileName(file);
        Path filePath = buildFilePath(url, fileName);


        // 디렉토리가 없을 시 디렉토리를 추가해주는 기능과 저장 기능.
        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        return buildPublicUrl(url, fileName);
    }

    //리스트용
    public List<String> storeAll(ImageUrl url, List<MultipartFile> files) {
        List<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            result.add(store(url, file));
        }
        return result;
    }

    //배열용
    public List<String> storeAll(ImageUrl url, MultipartFile[] files) {
        return storeAll(url, Arrays.asList(files));
    }

    //삭제
    public boolean delete(ImageUrl url, String fileName){
        Path filePath = buildFilePath(url, fileName);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new CustomException(ExceptionMessageEnum.FAILED_DELETE_FILE);
        }

    }

    //리스트용
    public List<Boolean> deleteAll(ImageUrl url, List<String> files) {
        List<Boolean> result = new ArrayList<>();
        for (String file : files) {
            result.add(delete(url, file));
        }
        return result;
    }

    //배열용
    public List<Boolean> deleteAll(ImageUrl url, String[] files) {
        return deleteAll(url, Arrays.asList(files));
    }



    //파일명 겹치지 않도록 고유값을 추가하여 파일명 지정.
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    //키워드를 디렉토리 경로로 연결해주는 메서드
    private Path buildFilePath(ImageUrl url, String fileName) {
        return Paths.get(url.getUrl(), fileName);
    }

    // 반환해줄 주소와 파일명.
    private String buildPublicUrl(ImageUrl url, String fileName) {
        String path = url.getUrl();

        if (!path.startsWith("/")) path = "/" + path;
        if (!path.endsWith("/")) path += "/";

        return path + fileName;
    }
}
