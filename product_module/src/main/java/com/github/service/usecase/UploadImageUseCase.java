package com.github.service.usecase;

import com.github.common.exception.GlobalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Component
public class UploadImageUseCase {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Client s3Client;

    public UploadImageUseCase(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private String uploadImageFile(
            final MultipartFile multipartFile,
            final String fileName
    ) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));
            return getUrl(fileName);
        } catch (IOException e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 업로드 중 에러가 발생했습니다.");
        }
    }

    private String getUrl(String fileName) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(fileName)).toExternalForm();
    }

    public String exec(MultipartFile multipartFile, String fileName) {
        return this.uploadImageFile(multipartFile, fileName);
    }

}
