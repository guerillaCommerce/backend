package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class CreateFileNameUseCase {

    private String createFileName(final String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(final String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".webp")) {
                return extension;
            }
            throw new GlobalException(HttpStatus.BAD_REQUEST, "이미지 파일 확장자가 없습니다.");
        } catch (StringIndexOutOfBoundsException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "이미지 파일 형식이 올바르지 않습니다.");
        }
    }

    public String exec(final MultipartFile multipartFile) {
        return this.createFileName(multipartFile.getOriginalFilename());
    }
}
