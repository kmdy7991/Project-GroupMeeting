package com.groupmeeting.image;

import com.groupmeeting.global.exception.custom.NotImageRequestException;
import com.groupmeeting.global.image.S3ImageService;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class S3Test {
    @Mock
    S3Template s3Template;

    @InjectMocks
    private S3ImageService s3ImageService;

    @Test
    void uploadImage() throws NotImageRequestException, IOException {
        String fileName = "testImage.jpg";
        String folder = "test";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", "test image content".getBytes());

        String result = s3ImageService.uploadImage(folder, file);

        assertThat(result.endsWith("jpg")).isEqualTo(true);
    }

}
