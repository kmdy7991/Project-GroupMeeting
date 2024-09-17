package com.groupmeeting.global.image;

import com.groupmeeting.global.exception.custom.NotImageRequestException;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageService {
    private final S3Template s3Template;
    @Value("${s3.bucket}")
    private String bucketName;

    public String uploadImage(String folder, MultipartFile file) throws NotImageRequestException, IOException {
        var type = file.getContentType();
        if (type == null || !type.startsWith("image")) {
            throw new NotImageRequestException("이미지 형식만 업로드할 수 있습니다.");
        }

        String fileName = createName(folder, StringUtils.getFilenameExtension(file.getOriginalFilename()));

        try (InputStream is = file.getInputStream()) {
            S3Resource resource = s3Template.upload(
                    bucketName,
                    fileName,
                    is,
                    ObjectMetadata.builder()
                            .contentType(file.getContentType())
                            .build()
            );

//            return fileName;
            return resource.getFilename();
        }
    }

    public void deleteImage(String fileName) {
        s3Template.deleteObject(bucketName, fileName);
    }


    private String createName(String folder, String type) {
        return String.format("%s/%s.%s", folder, UUID.randomUUID().toString(), type);
    }
}
