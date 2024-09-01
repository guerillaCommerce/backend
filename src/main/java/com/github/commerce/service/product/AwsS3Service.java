package com.github.commerce.service.product;

import com.github.commerce.service.product.exception.ProductErrorCode;
import com.github.commerce.service.product.exception.ProductException;
import com.github.commerce.service.product.util.FilePathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String memoryUpload(MultipartFile uploadFile, String fileName) throws IOException {
        // 파일 업로드
        try (InputStream inputStream = uploadFile.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(uploadFile.getContentType())
                    .contentLength(uploadFile.getSize())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, uploadFile.getSize()));
            return getUrl(fileName);
        }
    }

    public void removeFile(String imageUrl) throws ProductException {
        String filePath = FilePathUtils.convertImageUrlToFilePath(imageUrl);
        log.info("filePath={}", filePath);
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .build();
            s3Client.headObject(headObjectRequest);
        } catch (NoSuchKeyException e) {
            throw new ProductException(ProductErrorCode.NOTFOUND_URL_IN_S3);
        }

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(filePath)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    private String putS3(File uploadFile, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));
        uploadFile.delete();
        return getUrl(fileName);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        log.info("파일 이름: {}", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        log.info("File: {}", convertFile.getName());
        return Optional.of(convertFile);
    }

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".webp")) {
                return extension;
            }
            throw new ProductException(ProductErrorCode.NOT_IMAGE_EXTENSION);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ProductException(ProductErrorCode.INVALID_FORMAT_FILE);
        }
    }

    private String getUrl(String fileName) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(fileName)).toExternalForm();
    }
}
