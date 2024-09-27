package com.groupmeeting.unit.image;

import com.groupmeeting.global.exception.custom.NotImageRequestException;
import com.groupmeeting.global.image.S3ImageService;

import io.awspring.cloud.s3.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class S3ImageServiceTest {
    private final S3Client client = mock(S3Client.class);

    private final S3OutputStreamProvider s3OutputStreamProvider =
            mock(S3OutputStreamProvider.class, Answers.RETURNS_DEEP_STUBS);

    private final S3ObjectConverter s3ObjectConverter = mock(S3ObjectConverter.class);

    private final S3Presigner s3Presigner = mock(S3Presigner.class);

    private final S3Template s3Template = new S3Template(client, s3OutputStreamProvider, s3ObjectConverter, s3Presigner);

    private S3ImageService s3ImageService;

    @BeforeEach
    public void setUp() {
        this.s3ImageService = new S3ImageService(s3Template, "temp");
    }

    @Test
    @DisplayName("이미지를 저장하고 파일명을 반환한다.")
    void uploadImage() throws NotImageRequestException, IOException {
        String fileName = "testImage.jpg";
        String folder = "test";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpg", "test image content".getBytes());

        String result = s3ImageService.uploadImage(folder, file);

        assertThat(result.endsWith("jpg")).isEqualTo(true);
    }

    @Test
    @DisplayName("콘텐츠 타입이 이미지가 아니라면 예외가 발생한다.")
    void uploadNotImage() {
        String fileName = "testImage.txt";
        String folder = "test";
        MultipartFile file = new MockMultipartFile("file", fileName, "text/plain", "test image content".getBytes());

        assertThrows(NotImageRequestException.class,
                () -> s3ImageService.uploadImage(folder, file));
    }

    @Test
    @DisplayName("저장된 이미지를 삭제할 수 있다.")
    void deleteImage() throws NotImageRequestException, IOException {
        String fileName = "testImage.jpg";
        String folder = "test";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", "test image content".getBytes());

        String result = s3ImageService.uploadImage(folder, file);

        s3ImageService.deleteImage(result);
    }

    @Test
    @DisplayName("저장된 이미지의 URL을 반환한다.")
    void findImage() throws NotImageRequestException, IOException {
        String fileName = "testImage.jpg";
        String folder = "test";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", "test image content".getBytes());

        String result = s3ImageService.uploadImage(folder, file);

        assertThat("https://temp.s3.ap-northeast-2.amazonaws.com" + result).isEqualTo(s3ImageService.getImageUrl(result));
    }
}
